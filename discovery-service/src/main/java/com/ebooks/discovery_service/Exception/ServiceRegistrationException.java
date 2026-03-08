package com.ebooks.discovery_service.Exception;

public class ServiceRegistrationException extends RuntimeException {
    public ServiceRegistrationException(String message) {
        super(message);
    }
    
    public ServiceRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
