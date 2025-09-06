package com.ebook.payment_service.Listener;

import com.ebook.payment_service.DTO.PaymentRequestDTO;
import com.ebook.payment_service.Events.OrderCreatedEvent;
import com.ebook.payment_service.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderListener {

    private final PaymentService paymentService;

    @KafkaListener(topics = "order-events", groupId = "payment-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(OrderCreatedEvent orderCreatedEvent) {
        try {
            log.info("Received OrderCreatedEvent for orderId: {}", orderCreatedEvent);
            PaymentRequestDTO paymentRequest = new PaymentRequestDTO();
            paymentRequest.setOrderId(orderCreatedEvent.getOrderId());
            paymentRequest.setCustomerId(orderCreatedEvent.getCustomerId());
            paymentRequest.setAmount(Double.parseDouble(orderCreatedEvent.getTotalAmount()));
            paymentService.processPayment(paymentRequest);
        } catch (Exception e) {
            log.error("Error processing orderCreatedEvent: {}", e.getMessage());
            throw new RuntimeException("Error processing orderCreatedEvent",e);
        }
    }



}
