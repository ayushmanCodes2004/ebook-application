package com.ebook.payment_service.Service;

import com.ebook.payment_service.DTO.PaymentRequestDTO;
import com.ebook.payment_service.DTO.PaymentResponseDTO;
import com.ebook.payment_service.DTO.PaymentStatus;
import com.ebook.payment_service.Entity.Payment;
import com.ebook.payment_service.Events.PaymentCompletedEvent;
import com.ebook.payment_service.Repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import static com.ebook.payment_service.DTO.PaymentStatus.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderClient orderClient;

    private final KafkaTemplate<String,String> kafkaTemplate;



    private final ObjectMapper objectMapper;

//    public PaymentService(PaymentRepository paymentRepository, OrderClient orderClient) {
//        this.paymentRepository = paymentRepository;
//        this.orderClient = orderClient;
//    }

    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO){

       String paymentId = generatePaymentId();

       Payment payment = new Payment();
       payment.setPaymentId(paymentId);
       payment.setOrderId(paymentRequestDTO.getOrderId());
       payment.setCustomerId(paymentRequestDTO.getCustomerId());
       payment.setAmount(paymentRequestDTO.getAmount());
       payment.setPaymentDate(LocalDate.now());
       boolean paymentSuccess = new Random().nextBoolean();
         if(paymentSuccess) {
              payment.setPaymentStatus(SUCCESS);
              payment.setTransactionId(UUID.randomUUID().toString());
//              orderClient.updateOrderStatus(paymentRequestDTO.getOrderId(), "CONFIRMED");


         } else {
              payment.setPaymentStatus(PaymentStatus.FAILED);
              payment.setTransactionId("N/A");
//              orderClient.updateOrderStatus(paymentRequestDTO.getOrderId(), "CANCELLED");



         }
         paymentRepository.save(payment);

       String status = paymentSuccess? "CONFIRMED":"CANCELLED";
       log.info("Payment {} successfully processed", paymentId);

         updatePaymentStatus(paymentRequestDTO.getOrderId(),status);


         PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
            paymentResponseDTO.setPaymentId(paymentId);
            paymentResponseDTO.setOrderId(payment.getOrderId());
            paymentResponseDTO.setAmount(payment.getAmount());
            paymentResponseDTO.setPaymentStatus(payment.getPaymentStatus());
            paymentResponseDTO.setTransactionId(payment.getTransactionId());
            paymentResponseDTO.setPaymentDate(payment.getPaymentDate());
            paymentResponseDTO.setCustomerId(payment.getCustomerId());

            return paymentResponseDTO;
    }



    private String generatePaymentId() {
        return "pay-" + UUID.randomUUID().toString().substring(0, 8);
    }


    public PaymentResponseDTO getPaymentByOrderId(String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        if (payment == null) {
            return null;
        }
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setPaymentId(payment.getPaymentId());
        paymentResponseDTO.setOrderId(payment.getOrderId());
        paymentResponseDTO.setAmount(payment.getAmount());
        paymentResponseDTO.setPaymentStatus(payment.getPaymentStatus());
        paymentResponseDTO.setTransactionId(payment.getTransactionId());
        paymentResponseDTO.setPaymentDate(payment.getPaymentDate());
        paymentResponseDTO.setCustomerId(payment.getCustomerId());

        return paymentResponseDTO;
    }

    public void updatePaymentStatus(String orderId, String status) {
        try {
            PaymentCompletedEvent event = new PaymentCompletedEvent();
            event.setOrderId(orderId);
            event.setPaymentStatus(status);
            String eventString = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("payment-events", eventString);

            log.info("Sent PaymentCompletedEvent to Kafka for orderId: {}", orderId);
        } catch (Exception e) {
            log.error("Failed to update payment status for order: {}", orderId);
            throw new RuntimeException("Failed to update payment status ",e);
        }


    }
}
