package com.ndt.capstone.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDTO {
    private Long orderId;            // Mã đơn hàng
    private LocalDateTime createDate;// Thời gian đặt hàng
    private String paymentMethod;    // Hình thức thanh toán
    private BigDecimal total;        // Tổng giá trị thanh toán
    private String status;           // Trạng thái (PAID, PENDING, CANCELED)
    private String note;             // Ghi chú
    private List<OrderItemDTO> items;// Danh sách sản phẩm trong đơn
}