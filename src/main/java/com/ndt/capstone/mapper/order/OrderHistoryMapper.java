package com.ndt.capstone.mapper.order;

import java.util.ArrayList;
import java.util.List;

import com.ndt.capstone.dto.order.OrderHistoryDTO;
import com.ndt.capstone.dto.order.OrderItemDTO;
import com.ndt.capstone.entity.OrderEntity;
import com.ndt.capstone.entity.OrderVariantEntity;
import com.ndt.capstone.entity.ProductVariantEntity;
import com.ndt.capstone.entity.ProductEntity;


public class OrderHistoryMapper {

    public static OrderHistoryDTO toDTO(OrderEntity order, List<OrderVariantEntity> items) {
        OrderHistoryDTO dto = new OrderHistoryDTO();

        dto.setOrderId(order.getId());
        dto.setCreateDate(order.getCreateDate());
        dto.setTotal(order.getTotal());
        dto.setNote(order.getNote());

        // Lấy tên trạng thái thanh toán (PAID, PENDING, CANCELED)
        if (order.getStatus() != null) {
            dto.setStatus(order.getStatus().getName());
        }

        // Lấy tên phương thức thanh toán (bank transfer, cash-based, ...)
        if (order.getPayment() != null) {
            dto.setPaymentMethod(order.getPayment().getName());
        }

        // Chuyển đổi danh sách sản phẩm trong đơn hàng sang DTO
        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        for (OrderVariantEntity orderVariant : items) {
            OrderItemDTO itemDTO = toItemDTO(orderVariant);
            itemDTOs.add(itemDTO);
        }
        dto.setItems(itemDTOs);

        return dto;
    }


    private static OrderItemDTO toItemDTO(OrderVariantEntity orderVariant) {
        OrderItemDTO itemDTO = new OrderItemDTO();

        itemDTO.setQuantity(orderVariant.getQuantity());
        itemDTO.setPrice(orderVariant.getPrice());

        // Lấy thông tin variant (sku, hình ảnh, màu sắc, kích thước)
        ProductVariantEntity variant = orderVariant.getVariant();
        if (variant != null) {
            itemDTO.setSku(variant.getSku());

            String images = variant.getImages();
            if (images != null && !images.isBlank()) {
                String firstImage = images.split(",")[0].trim();
                // Lấy tên sản phẩm từ bảng product
                ProductEntity product = variant.getProduct();
                if (product != null) {
                    itemDTO.setProductName(product.getName());
                    // Ghép đường dẫn brand/productName/firstImage (chuẩn theo backend FileController)
                    String brandName = (product.getBrand() != null) ? product.getBrand().getName() : "";
                    String productNameFormatted = (product.getName() != null) ? product.getName().replace(" ", "_") : "";
                    if (!brandName.isBlank() && !productNameFormatted.isBlank()) {
                        itemDTO.setImage(brandName + "/" + productNameFormatted + "/" + firstImage);
                    } else {
                        itemDTO.setImage(firstImage);
                    }
                }
            } else {
                ProductEntity product = variant.getProduct();
                if (product != null) {
                    itemDTO.setProductName(product.getName());
                }
            }

            // Lấy tên màu sắc
            if (variant.getColor() != null) {
                itemDTO.setColor(variant.getColor().getName());
            }

            // Lấy tên kích thước
            if (variant.getSize() != null) {
                itemDTO.setSize(variant.getSize().getName());
            }

            // Lấy tên sản phẩm từ bảng product
            ProductEntity product = variant.getProduct();
            if (product != null) {
                itemDTO.setProductName(product.getName());
            }
        }

        return itemDTO;
    }
}