package com.ndt.capstone.service;

import com.ndt.capstone.dto.checkout.CheckoutDTO;
import com.ndt.capstone.entity.*;
import com.ndt.capstone.mapper.OrderPaymentPayloadMapper;
import com.ndt.capstone.payload.request.payment.BillingDetailsRequest;
import com.ndt.capstone.payload.request.payment.CheckoutRequest;
import com.ndt.capstone.payload.request.payment.OrderItemRequest;
import com.ndt.capstone.repository.*;
import com.ndt.capstone.service.contract.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    // 1. Nhóm lưu dữ liệu (ghi vào 3 bảng DB)
    private final OrderRepository orderRepository;               // Ghi vào bảng `orders`
    private final OrderVariantRepository orderVariantRepository; // Ghi vào bảng `order_variant`
    private final BillingDetailsRepository billingDetailsRepository; // Ghi vào bảng `billing_details`



    // 2. Nhóm kiểm tra tính hợp lệ (tìm kiếm xem có tồn tại không)
    private final UserRepository userRepository;                 // Kiểm tra User đang mua có trong DB không
    private final PaymentMethodRepository paymentMethodRepository; // Kiểm tra phương thức thanh toán hợp lệ
    private final CountryRepository countryRepository;           // Kiểm tra Quốc gia có hợp lệ không
    private final VariantRepository variantRepository; // Kiểm tra Sản phẩm trong giỏ có thật không

    private final OutboxEventRepository outboxEventRepository;
    private final OrderPaymentPayloadMapper orderPaymentPayloadMapper;

    @Value("${vietqr.bank-id}")
    private String bankId;

    @Value("${vietqr.account-no}")
    private String accountNo;

    @Value("${vietqr.account-name}")
    private String accountName;


    @Override
    @Transactional
    public CheckoutDTO processCheckout (CheckoutRequest request, Long userId){

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy User!"));
        PaymentMethodEntity paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Phương thức thanh toán không hợp lệ!"));

        CountryEntity country = countryRepository.findById(request.getBilling().getCountryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quốc gia!"));

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setPayment(paymentMethod);
        order.setTotal(request.getTotalAmount());
        order.setNote("PENDING_PAYMENT"); // Đánh dấu đơn đang chờ chuyển khoản
        order.setCreateDate(new java.sql.Timestamp(System.currentTimeMillis()));
        OrderEntity savedOrder = orderRepository.save(order); //


        for (OrderItemRequest item : request.getItems()) {
            // 1. Tìm sản phẩm trong kho theo SKU
            ProductVariantEntity variant = variantRepository.findById(item.getSkuVariant()).orElseThrow(() -> new RuntimeException("Không tìm tấy sản phẩm trong kho"));
            // 2. Gán vào bảng order_variant
            OrderVariantEntity orderVariant = new OrderVariantEntity();
            orderVariant.setOrder(savedOrder);   // Gán vào đơn hàng #101 vừa tạo ở Bước 2
            orderVariant.setVariant(variant);    // Gán mã áo/quần
            orderVariant.setQuantity(item.getQuantity()); // Số lượng
            orderVariant.setPrice(item.getPrice());       // Giá mua lúc đó
            orderVariantRepository.save(orderVariant);
        }

        BillingDetailsEntity billing = new BillingDetailsEntity();
        billing.setOrder(savedOrder); // Gán vào đơn hàng #101 vừa tạo ở Bước 2
        billing.setFirstName(request.getBilling().getFirstName());
        billing.setLastName(request.getBilling().getLastName());
        billing.setCompanyName(request.getBilling().getCompanyName());
        billing.setCountry(country);
        billing.setAddress(request.getBilling().getAddress());
        billing.setTown(request.getBilling().getTown());
        billing.setState(request.getBilling().getState());
        billing.setZipCode(request.getBilling().getZipCode());
        billing.setPhone(request.getBilling().getPhone());
        billing.setEmail(request.getBilling().getEmail());
        billing.setCreateDate(new java.sql.Timestamp(System.currentTimeMillis()));
        billingDetailsRepository.save(billing);

        // 6. Tạo nội dung chuyển khoản và link QR
        String transferContent = "DH" + savedOrder.getId();
        long amountLong = savedOrder.getTotal().longValue();

        // Mã hóa tên "LAM HOANG DUNG" thành "LAM%20HOANG%20DUNG" để không bị lỗi dấu cách trên URL
        String encodedAccountName = java.net.URLEncoder.encode(accountName, java.nio.charset.StandardCharsets.UTF_8);

        // Ghép link VietQR bằng các biến cấu hình từ file yaml
        String qrUrl = String.format(
                "https://img.vietqr.io/image/%s-%s-compact2.png?amount=%d&addInfo=%s&accountName=%s",
                bankId,
                accountNo,
                amountLong,
                transferContent,
                encodedAccountName
        );

        // 7. Trả về DTO cho Frontend
        CheckoutDTO response = new CheckoutDTO();
        response.setOrderId(savedOrder.getId());
        response.setQrUrl(qrUrl);
        response.setAmount(savedOrder.getTotal());
        response.setTransferContent(transferContent);

        return response;

    }

    @Override
    @Transactional
    public void confirmPayment(Long orderId) {

        // Phần 1: Cập nhật trạng thái
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng #" + orderId));

        if (!"PENDING_PAYMENT".equals(order.getNote())) {
            throw new RuntimeException("Đơn hàng không ở trạng thái chờ thanh toán!");
        }

        order.setNote("PAID");
        orderRepository.save(order);

        // Phần 2: Lấy data
        BillingDetailsEntity billing = billingDetailsRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy billing của đơn #" + orderId));

        List<OrderVariantEntity> orderItems = orderVariantRepository.findByOrder_Id(orderId);

        // Phần 3: Gọi mapper để build payload → Service không cần biết chi tiết JSON
        String payload = orderPaymentPayloadMapper.buildPayload(order, billing, orderItems);

        // Phần 4: Insert outbox
        OutboxEventEntity event = OutboxEventEntity.builder()
                .aggregateId(order.getId())
                .eventType("ORDER_PAYMENT_SUCCESS")
                .topic("order.payment")
                .payload(payload)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .retryCount(0)
                .build();

        outboxEventRepository.save(event);
    }
}
