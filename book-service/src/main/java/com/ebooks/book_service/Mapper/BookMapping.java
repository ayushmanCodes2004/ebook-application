package com.ebooks.book_service.Mapper;

import com.ebooks.book_service.DTO.BookRequestDTO;
import com.ebooks.book_service.DTO.BookResponseDTO;
import com.ebooks.book_service.Entity.Book;

public class BookMapping {

    public static BookResponseDTO toBookResponseDTO(Book book ){

        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setBookId(book.getBookId());
        bookResponseDTO.setTitle(book.getTitle());
        bookResponseDTO.setAuthor(book.getAuthor());
        bookResponseDTO.setDescription(book.getDescription());
        bookResponseDTO.setPrice(book.getPrice());
        bookResponseDTO.setStockQuantity(book.getStockQuantity());
        bookResponseDTO.setIsAvailable(book.getIsAvailable());
        bookResponseDTO.setGenreName(book.getGenre().getGenreName());

        return bookResponseDTO;
    }

    public static Book toBookEntity(BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setDescription(bookRequestDTO.getDescription());
        book.setPrice(bookRequestDTO.getPrice());
        book.setStockQuantity(bookRequestDTO.getStockQuantity());
        book.setIsAvailable(bookRequestDTO.getStockQuantity() > 0);

        return book;
    }
}
