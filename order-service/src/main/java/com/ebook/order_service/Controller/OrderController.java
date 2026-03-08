package com.ebook.order_service.Controller;

import com.ebook.order_service.DTO.OrderRequestDTO;
import com.ebook.order_service.DTO.OrderResponseDTO;
import com.ebook.order_service.DTO.OrderStatus;
import com.ebook.order_service.Exception.InvalidOrderRequestException;
import com.ebook.order_service.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        if (orderRequest.getCustomerId() == null || orderRequest.getOrderedBooks() == null || orderRequest.getOrderedBooks().isEmpty()) {
           throw new InvalidOrderRequestException("Invalid order request: Customer ID and ordered books are required");
        }
        OrderResponseDTO orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @GetMapping("/id/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderByOrderId(@PathVariable String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            throw new InvalidOrderRequestException("Order ID cannot be null or empty");
        }
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            throw new InvalidOrderRequestException("Customer ID cannot be null or empty");
        }
        return new ResponseEntity<>(orderService.getOrderByCustomerId(customerId), HttpStatus.OK);
    }

  @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
        if (orderId == null || orderId.isEmpty()) {
            throw new InvalidOrderRequestException("Order ID cannot be null or empty");
        }
        if (status == null) {
            throw new InvalidOrderRequestException("Order status cannot be null");
        }
         orderService.updateOrderStatus(orderId, status);
        return new ResponseEntity<>("Order status updated to "+status.name(), HttpStatus.OK);
    }

    @GetMapping("/validateCustomer/{customerId}")
    public OrderResponseDTO geByCustomerId(@PathVariable String customerId) {
        return orderService.getOrderByCustomerIdForValidation(customerId);
    }


}
