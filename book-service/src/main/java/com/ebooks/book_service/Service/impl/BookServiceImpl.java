package com.ebooks.book_service.Service.impl;

import com.ebooks.book_service.DTO.BookRequestDTO;
import com.ebooks.book_service.DTO.BookResponseDTO;
import com.ebooks.book_service.Entity.Book;
import com.ebooks.book_service.Entity.Genre;
import com.ebooks.book_service.Repository.BookRepository;
import com.ebooks.book_service.Repository.GenreRepository;
import com.ebooks.book_service.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;


    @Override
    public List<BookResponseDTO> getAllBooks() {
       List<BookResponseDTO> fetchedBooks = bookRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .toList();
        return fetchedBooks;
    }

    @Override
    public BookResponseDTO getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        return convertToResponseDTO(book);


    }

    @Override
    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
      Genre fetchedGenre = genreRepository.findById(bookRequestDTO.getGenreId()).orElseThrow(() -> new RuntimeException("Genre not found"));
        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setDescription(bookRequestDTO.getDescription());
        book.setStockQuantity(bookRequestDTO.getStockQuantity());
        book.setPrice(bookRequestDTO.getPrice());
        book.setIsAvailable(bookRequestDTO.getStockQuantity() > 0);
        book.setGenre(fetchedGenre);
        Book savedBook = bookRepository.save(book);
        return convertToResponseDTO(savedBook);
    }

    @Override
    public BookResponseDTO updateStockQuantity(Long bookId, Integer stockQuantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        book.setStockQuantity(book.getStockQuantity()+ stockQuantity);
        Book updatedBook = bookRepository.save(book);
        return convertToResponseDTO(updatedBook);
    }

    @Override
    public String deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        bookRepository.delete(book);
        return "Book with id: " + bookId + " has been deleted successfully.";
    }

    private BookResponseDTO convertToResponseDTO(Book book) {
        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setBookId(book.getBookId());
        responseDTO.setTitle(book.getTitle());
        responseDTO.setAuthor(book.getAuthor());
        responseDTO.setPrice(book.getPrice());
        responseDTO.setDescription(book.getDescription());
        responseDTO.setStockQuantity(book.getStockQuantity());
        responseDTO.setIsAvailable(book.getIsAvailable());
        if (book.getGenre() != null) {
            responseDTO.setGenreName(book.getGenre().getGenreName());
        }
        return responseDTO;
    }
}
