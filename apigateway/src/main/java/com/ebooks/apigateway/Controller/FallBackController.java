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

    @GetMapping("/fallback/order")
    public ResponseEntity<String> orderFallback(){
        return new ResponseEntity<>("Order-Service is temporarily unavailable. Please try again later.", HttpStatus.OK);
    }

    @GetMapping("/fallback/auth")
    public ResponseEntity<String> authFallback(){
        return new ResponseEntity<>("Auth-Service is temporarily unavailable. Please try again later.", HttpStatus.OK);
    }

    @GetMapping("/fallback/payment")
    public ResponseEntity<String> paymentFallback(){
        return new ResponseEntity<>("Payment-Service is temporarily unavailable. Please try again later.", HttpStatus.OK);
    }

}
