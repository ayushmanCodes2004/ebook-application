package com.ebook.payment_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private String paymentId;
    private String orderId;
    private String customerId;
    private PaymentStatus paymentStatus;
    private double amount;
    private String paymentDate;
    private String transactionId;
}
