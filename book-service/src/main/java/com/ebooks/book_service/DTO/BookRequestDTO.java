package com.ebooks.book_service.DTO;

import com.ebooks.book_service.Entity.Genre;
import lombok.Data;

@Data
public class BookRequestDTO {
    private String title;
    private String author;
    private Double price;
    private Integer stockQuantity;
    private String description;
    private Long genreId;





}
