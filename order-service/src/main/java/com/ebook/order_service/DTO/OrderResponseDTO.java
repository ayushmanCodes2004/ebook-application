package com.ebook.order_service.DTO;

import com.ebook.order_service.Entity.BookOrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private String orderId;
    private String customerId;
    private LocalDateTime orderDate;
    private double totalPrice;
    private OrderStatus status;
    private List<BookOrderItem> bookOrderItems;


}
