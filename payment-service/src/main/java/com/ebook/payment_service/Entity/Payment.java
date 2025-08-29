package com.ebook.payment_service.Entity;

import com.ebook.payment_service.DTO.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    private String paymentId;
    private String orderId;
    private String customerId;
    private PaymentStatus paymentStatus;
    private double amount;
    private LocalDateTime paymentDate;
    private String transactionId;



}
