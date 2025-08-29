package com.ebooks.book_service.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ExtendedGenreResponseDTO extends GenreResponseDTO {

    List<BookResponseDTO> books;

    public ExtendedGenreResponseDTO(Long genreId, String genreName, List<BookResponseDTO> books) {
        super(genreId, genreName);
        this.books = books;
    }

}
