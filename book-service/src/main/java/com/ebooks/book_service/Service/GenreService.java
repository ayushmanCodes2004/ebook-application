package com.ebooks.book_service.Service;

import com.ebooks.book_service.DTO.ExtendedGenreResponseDTO;
import com.ebooks.book_service.DTO.GenreRequestDTO;
import com.ebooks.book_service.DTO.GenreResponseDTO;

import java.util.List;

public interface GenreService {

    List<ExtendedGenreResponseDTO> getAllGenres();

    ExtendedGenreResponseDTO getGenreById(Long genreId);

    GenreResponseDTO createGenre(GenreRequestDTO genreRequestDTO);

    GenreResponseDTO updateGenre(Long genreId, GenreRequestDTO genreRequestDTO);

    String deleteGenre(Long genreId);

}
