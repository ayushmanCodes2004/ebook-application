package com.ebook.payment_service.Listener;

import com.ebook.payment_service.DTO.PaymentRequestDTO;
import com.ebook.payment_service.Events.OrderCreatedEvent;
import com.ebook.payment_service.Service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderListener {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-events", groupId = "payment-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message) {
        try {
            log.info("Received message: {}", message);
            OrderCreatedEvent orderCreatedEvent = objectMapper.readValue(message, OrderCreatedEvent.class);
            log.info("Deserialized OrderCreatedEvent: {}", orderCreatedEvent);
            PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
            paymentRequestDTO.setOrderId(orderCreatedEvent.getOrderId());
            paymentRequestDTO.setCustomerId(orderCreatedEvent.getCustomerId());
            paymentRequestDTO.setAmount(Double.parseDouble(orderCreatedEvent.getTotalAmount()));
            paymentService.processPayment(paymentRequestDTO);
            log.info("Payment processed for orderId: {}", orderCreatedEvent.getOrderId());
        } catch (Exception e) {
            log.error("Error processing orderCreatedEvent: {}", e.getMessage());
            throw new RuntimeException("Error processing orderCreatedEvent",e);
        }
    }



}
