package com.ebook.order_service.Listener;

import com.ebook.order_service.DTO.OrderStatus;
import com.ebook.order_service.Events.PaymentCompletedEvent;
import com.ebook.order_service.Service.AuthClient;
import com.ebook.order_service.Service.EmailPublisher;
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
    private final EmailPublisher emailPublisher;
    private final AuthClient authClient;

    @KafkaListener(topics ="payment-events", groupId = "order-service-group")
    public void consume(String event){
        try {
            log.info("Received PaymentCompletedEvent : {}", event);
            PaymentCompletedEvent event1 = objectMapper.readValue(event, PaymentCompletedEvent.class);
            log.info("Deserialize PaymentCompletedEvent: {}", event1);


            orderService.updateOrderStatus(event1.getOrderId(), OrderStatus.valueOf(event1.getPaymentStatus()));

            log.info("Order status updated for orderId: {}", event1.getOrderId());


            if ("CONFIRMED".equals(event1.getPaymentStatus())) {
                try {
                    String customerId = orderService.getOrderById(event1.getOrderId()).getCustomerId();
                    String customerEmail = authClient.validEmail(customerId);
                    
                    String subject = "Order Confirmation - Order #" + event1.getOrderId();
                    String body = "Dear Customer,\n\n" +
                            "We are pleased to inform you that your payment has been successfully processed.\n\n" +
                            "📌 Order Details:\n" +
                            "• Order ID: " + event1.getOrderId() + "\n" +
                            "• Payment Status: " + event1.getPaymentStatus() + "\n\n" +
                            "Thank you for shopping with us. You can track your order status anytime from your account.\n\n" +
                            "If you have any queries, feel free to reach out to our support team.\n\n" +
                            "Best regards,\n" +
                            "The E-Book Store Team";
                    
                    emailPublisher.publishEmailMessage(customerEmail, subject, body);
                    log.info("Email notification sent for confirmed order: {}", event1.getOrderId());
                } catch (Exception emailEx) {
                    log.error("Failed to send email notification for order: {}", event1.getOrderId(), emailEx);

                }
            }

        }


         catch(JsonProcessingException e) {
            log.error("Error deserializing PaymentCompletedEvent: {}", e.getMessage());
            throw new RuntimeException("Error deserializing PaymentCompletedEvent",e);
        }
        catch (Exception e) {
            log.error("Error processing PaymentCompletedEvent: {}", e.getMessage());
            throw new RuntimeException("Error processing PaymentCompletedEvent",e);
        }


    }
}
