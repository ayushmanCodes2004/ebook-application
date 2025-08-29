package com.ebooks.book_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "GENRE")
public class Genre {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long genreId;
    private String genreName;

    @OneToMany(mappedBy = "genre",cascade = jakarta.persistence.CascadeType.ALL)
    private List<Book> books ;





}
