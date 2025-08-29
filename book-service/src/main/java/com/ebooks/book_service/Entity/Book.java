package com.ebooks.book_service.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long bookId;
    private String title;
    private String author;
    private String description;
    private Boolean isAvailable;
    private Double price;
    private Integer stockQuantity;
    private LocalDateTime publicationDate;
    private LocalDateTime stockUpdateDate;


    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;


    @PrePersist
    public void onCreate() {
        isAvailable = stockQuantity != null && stockQuantity > 0;
        publicationDate = LocalDateTime.now();
        stockUpdateDate = LocalDateTime.now();

    }

    @PreUpdate
    public void onUpdate() {
        isAvailable = stockQuantity != null && stockQuantity > 0;
        stockUpdateDate = LocalDateTime.now();

    }

}


