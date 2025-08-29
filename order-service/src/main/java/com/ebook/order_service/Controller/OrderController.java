package com.ebook.order_service.Controller;

import com.ebook.order_service.DTO.OrderRequestDTO;
import com.ebook.order_service.DTO.OrderResponseDTO;
import com.ebook.order_service.DTO.OrderStatus;
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
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        if (orderRequest.getCustomerId() == null || orderRequest.getOrderedBooks() == null || orderRequest.getOrderedBooks().isEmpty()) {
           return new ResponseEntity<>("Invalid order request", HttpStatus.BAD_REQUEST);
        }
        OrderResponseDTO orderResponse = orderService.placeOrder(orderRequest);

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);

    }

    @GetMapping("/id/{orderId}")
    public ResponseEntity<?> getOrderByOrderId(@PathVariable String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return new ResponseEntity<>("Orders ID cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            return new ResponseEntity<>("Customer ID cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderService.getOrderByCustomerId(customerId), HttpStatus.OK);
    }

  @PatchMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
        if (orderId == null || orderId.isEmpty()) {
            return new ResponseEntity<>("Orders ID cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        if (status == null) {
            return new ResponseEntity<>("Orders status cannot be null", HttpStatus.BAD_REQUEST);
        }
         orderService.updateOrderStatus(orderId, status);
        return new ResponseEntity<>("Orders status updated to "+status.name(), HttpStatus.OK);
    }


}
