package com.ebook.payment_service.Service;

import com.ebook.payment_service.DTO.UserResponseDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthClient {
    private final RestTemplate restTemplate;

    public AuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String validEmail(String customerId) {

        String url = "http://auth-service/auth/validateCustomerId/{customerId}";

        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                UserResponseDTO.class,
                customerId


        );

        return response.getBody().getEmail();

    }


}
