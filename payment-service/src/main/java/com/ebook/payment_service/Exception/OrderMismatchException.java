package com.ebook.payment_service.Exception;

public class OrderMismatchException extends RuntimeException {
    public OrderMismatchException(String message) {
        super(message);
    }
}
