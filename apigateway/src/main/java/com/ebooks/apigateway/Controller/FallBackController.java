package com.ebooks.apigateway.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/fallback/book")
    public ResponseEntity<String> fallback(){
        return new ResponseEntity<>("Book-Service is temporarily unavailable. Please try again later.", HttpStatus.OK);
    }
}
