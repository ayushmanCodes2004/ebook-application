package com.ebooks.book_service.Controller;

import com.ebooks.book_service.DTO.BookRequestDTO;
import com.ebooks.book_service.DTO.BookResponseDTO;
import com.ebooks.book_service.Service.impl.BookServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookServiceImpl bookServiceImpl;

    public BookController(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @PostMapping
    public BookResponseDTO createBook(@RequestBody BookRequestDTO bookRequestDTO){
        return bookServiceImpl.createBook(bookRequestDTO);
    }

    @GetMapping()
    public List<BookResponseDTO> getAllBooks() {
        return bookServiceImpl.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public BookResponseDTO getBookById(@PathVariable Long bookId) {
        return bookServiceImpl.getBookById(bookId);
    }

    @PatchMapping("/{bookId}/stock")
    public BookResponseDTO updateStockQuantity(@PathVariable Long bookId, @RequestParam Integer stockQuantity) {
        return bookServiceImpl.updateStockQuantity(bookId, stockQuantity);
    }


    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        return bookServiceImpl.deleteBook(bookId);
    }


}
