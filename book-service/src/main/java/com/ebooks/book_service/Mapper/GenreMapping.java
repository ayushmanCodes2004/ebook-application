package com.ebooks.book_service.Mapper;

import com.ebooks.book_service.DTO.GenreRequestDTO;
import com.ebooks.book_service.DTO.GenreResponseDTO;
import com.ebooks.book_service.Entity.Genre;

import java.util.ArrayList;

public class GenreMapping {

    public static GenreResponseDTO toGenreResponseDTO(Genre genre) {
        GenreResponseDTO genreResponseDTO = new GenreResponseDTO();
        genreResponseDTO.setGenreId(genre.getGenreId());
        genreResponseDTO.setGenreName(genre.getGenreName());
        return genreResponseDTO;
    }

    public static Genre toGenreEntity(GenreRequestDTO genreRequestDTO) {
        Genre genre = new Genre();
        genre.setGenreName(genreRequestDTO.getGenreName());
        return genre;
    }


}
