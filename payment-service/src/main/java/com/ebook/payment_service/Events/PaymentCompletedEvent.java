package com.ebook.payment_service.Events;

import com.ebook.payment_service.DTO.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCompletedEvent {
    private String orderId;
    private String paymentStatus;
}
