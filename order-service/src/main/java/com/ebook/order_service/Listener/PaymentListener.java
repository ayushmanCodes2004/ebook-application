package com.ebook.order_service.Listener;

import com.ebook.order_service.DTO.OrderStatus;
import com.ebook.order_service.Events.PaymentCompletedEvent;
import com.ebook.order_service.Service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentListener {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics ="payment-events", groupId = "order-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String event){
        try {
            log.info("Received PaymentCompletedEvent : {}", event);
            PaymentCompletedEvent event1 = objectMapper.readValue(event, PaymentCompletedEvent.class);
            log.info("Deserialize PaymentCompletedEvent: {}", event1);


            orderService.updateOrderStatus(event1.getOrderId(), OrderStatus.valueOf(event1.getPaymentStatus()));
            log.info("Order status updated for orderId: {}", event1.getOrderId());
        } catch(JsonProcessingException e) {
            log.error("Error deserializing PaymentCompletedEvent: {}", e.getMessage());
            throw new RuntimeException("Error deserializing PaymentCompletedEvent",e);
        }
        catch (Exception e) {
            log.error("Error processing PaymentCompletedEvent: {}", e.getMessage());
            throw new RuntimeException("Error processing PaymentCompletedEvent",e);
        }


    }
}
