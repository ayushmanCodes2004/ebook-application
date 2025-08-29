package com.ebook.order_service.DTO;

import lombok.Data;

@Data
public class BookOrderItemRequestDTO {
    private String bookId;
    private int quantity;
}
