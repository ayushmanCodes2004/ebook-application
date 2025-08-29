package com.ebooks.book_service.Service;

import com.ebooks.book_service.DTO.BookRequestDTO;
import com.ebooks.book_service.DTO.BookResponseDTO;

import java.util.List;

public interface BookService {

     List<BookResponseDTO> getAllBooks();

     BookResponseDTO getBookById(Long bookId);

     BookResponseDTO createBook(BookRequestDTO bookRequestDTO);

     BookResponseDTO updateStockQuantity(Long bookId, Integer stockQuantity);

     String deleteBook(Long bookId);




}
