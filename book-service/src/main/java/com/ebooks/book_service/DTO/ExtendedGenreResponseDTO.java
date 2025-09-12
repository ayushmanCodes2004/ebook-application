package com.ebooks.book_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedGenreResponseDTO extends GenreResponseDTO implements Serializable {

    List<BookResponseDTO> books;

    public ExtendedGenreResponseDTO(Long genreId, String genreName, List<BookResponseDTO> books) {
        super(genreId, genreName);
        this.books = books;
    }

}
