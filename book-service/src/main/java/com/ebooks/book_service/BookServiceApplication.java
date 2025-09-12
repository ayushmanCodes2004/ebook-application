package com.ebooks.book_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

}
