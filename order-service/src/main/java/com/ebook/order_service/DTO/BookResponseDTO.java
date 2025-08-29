package com.ebook.order_service.DTO;

import lombok.Data;

@Data
public class BookResponseDTO {
    private String bookId;
    private String title;
    private double price;
    private int stockQuantity;

}
