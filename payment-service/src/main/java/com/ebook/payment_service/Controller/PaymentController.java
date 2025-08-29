package com.ebook.payment_service.Controller;

import com.ebook.payment_service.DTO.PaymentRequestDTO;
import com.ebook.payment_service.DTO.PaymentResponseDTO;
import com.ebook.payment_service.Service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        return new ResponseEntity<>(paymentService.processPayment(paymentRequest), HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentDetails(@PathVariable String orderId) {
        return new ResponseEntity<>(paymentService.getPaymentByOrderId(orderId), HttpStatus.OK);
    }

}
