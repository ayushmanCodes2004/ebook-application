package com.ebook.order_service.Service;

import com.ebook.order_service.DTO.*;
import com.ebook.order_service.Entity.BookOrderItem;
import com.ebook.order_service.Entity.Orders;
import com.ebook.order_service.Events.OrderCreatedEvent;
import com.ebook.order_service.Repository.BookOrderItemRepository;
import com.ebook.order_service.Repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookOrderItemRepository bookOrderItemRepository;
    private final BookClient bookClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {

        String orderId = generateOrderId();

        double totalPrice = 0.0;

        List<BookOrderItem> orderItems = new ArrayList<>();

        for (BookOrderItemRequestDTO bookOrderItemRequestDTO : orderRequestDTO.getOrderedBooks()) {
            BookResponseDTO book = bookClient.getBookTitle(bookOrderItemRequestDTO.getBookId());
            if(book.getStockQuantity() < bookOrderItemRequestDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock for book: " + book.getTitle());

            }
            bookClient.updateBookStock(bookOrderItemRequestDTO.getBookId(),-bookOrderItemRequestDTO.getQuantity());
            double bookTotal = book.getPrice() * bookOrderItemRequestDTO.getQuantity();
            totalPrice += bookTotal;

            BookOrderItem bookOrderItem = new BookOrderItem(generateBookOrderItemId(),orderId,
                    bookOrderItemRequestDTO.getBookId(), bookOrderItemRequestDTO.getQuantity(), book.getPrice());
            orderItems.add(bookOrderItem);



        }
        Orders order = new Orders(orderId, orderRequestDTO.getCustomerId(),LocalDateTime.now(),
                totalPrice, OrderStatus.PENDING);
        orderRepository.save(order);
        bookOrderItemRepository.saveAll(orderItems);

        placeOrderEvent(order);

        return new OrderResponseDTO(order.getOrderId(), order.getCustomerId(),
                order.getOrderDate(), order.getTotalPrice(), order.getStatus(), orderItems);

    }

    public OrderResponseDTO getOrderById(String orderId) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orders not found with id: " + orderId));

        List<BookOrderItem> bookOrderItems = bookOrderItemRepository.findByOrderId(orderId);

        return new OrderResponseDTO(orders.getOrderId(), orders.getCustomerId(),
                orders.getOrderDate(), orders.getTotalPrice(), orders.getStatus(), bookOrderItems);
    }

    public List<OrderResponseDTO> getOrderByCustomerId(String customerId) {
        List<Orders> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found for customer with id: " + customerId);
        }

        List<OrderResponseDTO> orderResponses = new ArrayList<>();
        for (Orders order : orders) {
            List<BookOrderItem> bookOrderItems = bookOrderItemRepository.findByOrderId(order.getOrderId());
            orderResponses.add(new OrderResponseDTO(order.getOrderId(), order.getCustomerId(),
                    order.getOrderDate(), order.getTotalPrice(), order.getStatus(), bookOrderItems));
        }

        return orderResponses;
    }

    public void updateOrderStatus(String orderId, OrderStatus status) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orders not found with id: " + orderId));

        orders.setStatus(status);
        orderRepository.save(orders);
    }

    private String generateOrderId() {
        return "ord" + UUID.randomUUID().toString().substring(0, 8);
    }

    private String generateBookOrderItemId() {
        return "boi" + UUID.randomUUID().toString().substring(0, 8);
    }

    private void placeOrderEvent(Orders order) {
        try {
            OrderCreatedEvent event = new OrderCreatedEvent();
            event.setOrderId(order.getOrderId());
            event.setCustomerId(order.getCustomerId());
            event.setTotalAmount(String.valueOf(order.getTotalPrice()));
            String eventString = objectMapper.writeValueAsString(event);
            log.info("Sending OrderCreatedEvent to Kafka for orderId: {}", order.getOrderId());
            kafkaTemplate.send("order-events", eventString);
            log.info("OrderCreatedEvent sent to Kafka for orderId: {}", order.getOrderId());
        } catch (Exception e) {
            log.error("Failed to send OrderCreatedEvent to Kafka for orderId: {}", order.getOrderId(), e);
            throw new RuntimeException("Failed to send OrderCreatedEvent to Kafka", e);
        }
    }

}









