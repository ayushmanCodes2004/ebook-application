package com.ebook.order_service.Service;

import com.ebook.order_service.DTO.UserResponseDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthClient {

    private final RestTemplate restTemplate;

    public AuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String validCustomerId(String customerId) {

        String url = "http://auth-service/auth/validateCustomerId/{customerId}";

        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                UserResponseDTO.class,
                customerId


        );

        return response.getBody().getCustomerId();

    }
}
