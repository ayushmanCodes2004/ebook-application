package com.ebooks.book_service.DTO;

import com.ebooks.book_service.Entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {
    private String title;
    private String author;
    private Double price;
    private Integer stockQuantity;
    private String description;
    private Long genreId;





}
