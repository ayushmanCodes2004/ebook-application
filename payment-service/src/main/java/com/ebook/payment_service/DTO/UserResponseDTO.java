package com.ebook.payment_service.DTO;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String userName;
    private String customerId;
    private String email;
}
