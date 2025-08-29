package com.ebooks.book_service.DTO;

import lombok.Data;

@Data
public class BookResponseDTO {
    private Long bookId;
    private String title;
    private String author;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String genreName;
    private Boolean isAvailable;
}
