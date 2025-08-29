package com.ebooks.book_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreResponseDTO {
    private Long genreId;
    private String genreName;



}
