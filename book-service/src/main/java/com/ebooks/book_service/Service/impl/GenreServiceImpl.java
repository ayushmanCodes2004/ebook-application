package com.ebooks.book_service.Service.impl;

import com.ebooks.book_service.DTO.ExtendedGenreResponseDTO;
import com.ebooks.book_service.DTO.GenreRequestDTO;
import com.ebooks.book_service.DTO.GenreResponseDTO;
import com.ebooks.book_service.Entity.Book;
import com.ebooks.book_service.Entity.Genre;
import com.ebooks.book_service.Mapper.BookMapping;
import com.ebooks.book_service.Mapper.GenreMapping;
import com.ebooks.book_service.Repository.GenreRepository;
import com.ebooks.book_service.Service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<ExtendedGenreResponseDTO> getAllGenres() {
        List<Genre> genreList = genreRepository.findAll();
        List<ExtendedGenreResponseDTO> genreResponseDTOS = new ArrayList<>();
        for(Genre genre : genreList) {
            ExtendedGenreResponseDTO genreResponseDTO = convertToExtendedGenreResponseDTO(genre);
            genreResponseDTOS.add(genreResponseDTO);
        }
        return genreResponseDTOS;


    }

    @Override
    public ExtendedGenreResponseDTO getGenreById(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + genreId));
        return convertToExtendedGenreResponseDTO(genre);
    }

    @Override
    public GenreResponseDTO createGenre(GenreRequestDTO genreRequestDTO) {
        Genre genre = GenreMapping.toGenreEntity(genreRequestDTO);
        Genre savedGenre = genreRepository.save(genre);
        return GenreMapping.toGenreResponseDTO(savedGenre);
    }

    @Override
    public GenreResponseDTO updateGenre(Long genreId, GenreRequestDTO genreRequestDTO) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + genreId));
        genre.setGenreName(genreRequestDTO.getGenreName());
        Genre updatedGenre = genreRepository.save(genre);
        return GenreMapping.toGenreResponseDTO(updatedGenre);
    }

    @Override
    public String deleteGenre(Long genreId) {
        if (!genreRepository.existsById(genreId)) {
            throw new RuntimeException("Genre not found with id: " + genreId);
        }
        genreRepository.deleteById(genreId);
        return "Genre with ID " + genreId + " has been deleted successfully.";

    }

    private ExtendedGenreResponseDTO convertToExtendedGenreResponseDTO(Genre genre) {
       List<Book> bookList = genre.getBooks();
       return  new ExtendedGenreResponseDTO(
                genre.getGenreId(),
                genre.getGenreName(),
                bookList.stream().map(BookMapping::toBookResponseDTO).toList());

    }

}
