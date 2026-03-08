package com.ebooks.book_service.Service.impl;

import com.ebooks.book_service.DTO.BookRequestDTO;
import com.ebooks.book_service.DTO.BookResponseDTO;
import com.ebooks.book_service.Entity.Book;
import com.ebooks.book_service.Entity.Genre;
import com.ebooks.book_service.Exception.BookNotFoundException;
import com.ebooks.book_service.Exception.GenreNotFoundException;
import com.ebooks.book_service.Exception.InvalidStockQuantityException;
import com.ebooks.book_service.Repository.BookRepository;
import com.ebooks.book_service.Repository.GenreRepository;
import com.ebooks.book_service.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;


    @Override
    @Cacheable(value = "allbooks")
    public List<BookResponseDTO> getAllBooks() {
       List<BookResponseDTO> fetchedBooks = bookRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .toList();
        return fetchedBooks;
    }

    @Override
    @Cacheable(value = "books", key = "#bookId")
    public BookResponseDTO getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));
        return convertToResponseDTO(book);


    }

    @Override
    @Caching(evict = {@CacheEvict(value = "allbooks", allEntries = true),
            @CacheEvict(value = "allgenres", allEntries = true)})
    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
      Genre fetchedGenre = genreRepository.findById(bookRequestDTO.getGenreId())
              .orElseThrow(() -> new GenreNotFoundException("Genre not found with id: " + bookRequestDTO.getGenreId()));
        
        if (bookRequestDTO.getStockQuantity() < 0) {
            throw new InvalidStockQuantityException("Stock quantity cannot be negative");
        }
        
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
    @CachePut(value = "books", key ="#bookId")
    @Caching(evict = {@CacheEvict(value = "allbooks", allEntries = true),
    @CacheEvict(value = "allgenres", allEntries = true)})
    public BookResponseDTO updateStockQuantity(Long bookId, Integer stockQuantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));
        
        int newStockQuantity = book.getStockQuantity() + stockQuantity;
        if (newStockQuantity < 0) {
            throw new InvalidStockQuantityException("Insufficient stock. Current stock: " + book.getStockQuantity() + ", requested change: " + stockQuantity);
        }
        
        book.setStockQuantity(newStockQuantity);
        book.setIsAvailable(newStockQuantity > 0);
        Book updatedBook = bookRepository.save(book);
        return convertToResponseDTO(updatedBook);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "books", key ="#bookId"),
            @CacheEvict(value = "allbooks", allEntries = true),
            @CacheEvict(value = "allgenres", allEntries = true)
    })
    public String deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));
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
