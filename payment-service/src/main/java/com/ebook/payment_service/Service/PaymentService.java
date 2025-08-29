package com.ebook.payment_service.Service;

import com.ebook.payment_service.DTO.PaymentRequestDTO;
import com.ebook.payment_service.DTO.PaymentResponseDTO;
import com.ebook.payment_service.DTO.PaymentStatus;
import com.ebook.payment_service.Entity.Payment;
import com.ebook.payment_service.Repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

import static com.ebook.payment_service.DTO.PaymentStatus.PENDING;
import static com.ebook.payment_service.DTO.PaymentStatus.SUCCESS;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderClient orderClient;

    public PaymentService(PaymentRepository paymentRepository, OrderClient orderClient) {
        this.paymentRepository = paymentRepository;
        this.orderClient = orderClient;
    }

    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO){

       String paymentId = generatePaymentId();

       Payment payment = new Payment();
       payment.setPaymentId(paymentId);
       payment.setOrderId(paymentRequestDTO.getOrderId());
       payment.setCustomerId(paymentRequestDTO.getCustomerId());
       payment.setAmount(paymentRequestDTO.getAmount());
       payment.setPaymentDate(java.time.LocalDateTime.now());
       boolean paymentSuccess = new Random().nextBoolean();
         if(paymentSuccess) {
              payment.setPaymentStatus(SUCCESS);
              payment.setTransactionId(UUID.randomUUID().toString());
              orderClient.updateOrderStatus(paymentRequestDTO.getOrderId(), "CONFIRMED");
         } else {
              payment.setPaymentStatus(PaymentStatus.FAILED);
              payment.setTransactionId("N/A");
              orderClient.updateOrderStatus(paymentRequestDTO.getOrderId(), "CANCELLED");


         }
         paymentRepository.save(payment);

         PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
            paymentResponseDTO.setPaymentId(paymentId);
            paymentResponseDTO.setOrderId(payment.getOrderId());
            paymentResponseDTO.setAmount(payment.getAmount());
            paymentResponseDTO.setPaymentStatus(payment.getPaymentStatus());
            paymentResponseDTO.setTransactionId(payment.getTransactionId());

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

        return paymentResponseDTO;
    }
}
