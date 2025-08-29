package com.ebook.order_service.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private String customerId;
    private List<BookOrderItemRequestDTO> orderedBooks;
}
