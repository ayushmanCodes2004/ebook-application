package com.ebook.payment_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequestDTO {
    private String orderId;
    private String customerId;
    private double amount;

}
