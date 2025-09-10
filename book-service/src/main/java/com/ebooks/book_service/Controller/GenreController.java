package com.ebooks.book_service.Controller;

import com.ebooks.book_service.DTO.ExtendedGenreResponseDTO;
import com.ebooks.book_service.DTO.GenreRequestDTO;
import com.ebooks.book_service.DTO.GenreResponseDTO;
import com.ebooks.book_service.Service.impl.GenreServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreServiceImpl genreServiceImpl;

    public GenreController(GenreServiceImpl genreServiceImpl) {
        this.genreServiceImpl = genreServiceImpl;
    }

    @PostMapping
    public GenreResponseDTO createGenre(@RequestBody GenreRequestDTO genreRequestDTO) {
        return genreServiceImpl.createGenre(genreRequestDTO);
    }

    @GetMapping()
    public List<ExtendedGenreResponseDTO> getAllGenres() {
        return genreServiceImpl.getAllGenres();
    }

    @GetMapping("/{genreId}")
    public ExtendedGenreResponseDTO getGenreById(@PathVariable Long genreId) {
        return genreServiceImpl.getGenreById(genreId);
    }

    @PutMapping("/{genreId}/update")
    public GenreResponseDTO updateGenre(@PathVariable Long genreId, @RequestBody GenreRequestDTO genreRequestDTO) {
        return genreServiceImpl.updateGenre(genreId, genreRequestDTO);
    }

    @DeleteMapping("/{genreId}")
    public String deleteGenre(@PathVariable Long genreId) {
       return genreServiceImpl.deleteGenre(genreId);

    }


}
