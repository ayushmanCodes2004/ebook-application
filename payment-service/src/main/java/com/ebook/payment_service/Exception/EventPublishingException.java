package com.ebook.payment_service.Exception;

public class EventPublishingException extends RuntimeException {
    public EventPublishingException(String message) {
        super(message);
    }
    
    public EventPublishingException(String message, Throwable cause) {
        super(message, cause);
    }
}
