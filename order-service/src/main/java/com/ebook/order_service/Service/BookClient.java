package com.ebook.order_service.Service;

import com.ebook.order_service.DTO.BookResponseDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BookClient {

    private final RestTemplate restTemplate;

    public BookClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BookResponseDTO getBookTitle(String bookId) {
        String url = "http://book-service/books/" + bookId;
        return restTemplate.getForObject(url, BookResponseDTO.class);
    }

    public void updateBookStock(String bookId, Integer stockQuantity) {
        String url = "http://book-service/books/{bookId}/stock?stockQuantity={stockQuantity}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                entity,
                Void.class,
                bookId,
                stockQuantity
        );

    }





}
