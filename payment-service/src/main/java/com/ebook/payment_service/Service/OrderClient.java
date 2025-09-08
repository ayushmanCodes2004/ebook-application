package com.ebook.payment_service.Service;

import com.ebook.payment_service.DTO.GetOrderByOrderIdRequest;
import com.ebook.payment_service.DTO.OrderFromOrderServiceResponse;
import com.ebook.payment_service.DTO.OrderStatusUpdateRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderClient {

    private final RestTemplate restTemplate;

    public OrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void updateOrderStatus(String orderId, String status) {

        String url = "http://localhost:6001/orders/{orderId}/status?status={status}";

        OrderStatusUpdateRequest requestBody = new OrderStatusUpdateRequest(orderId, status);

        HttpEntity<OrderStatusUpdateRequest> requestEntity = new HttpEntity<>(requestBody);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class,
                orderId,
                status
        );

        System.out.println("Order status updated: " + response.getBody());
    }

    public Double getOrderAmount(String OrderId){

        GetOrderByOrderIdRequest request = new GetOrderByOrderIdRequest(OrderId);

        HttpEntity<GetOrderByOrderIdRequest> requestEntity = new HttpEntity<>(request);

        String url = "http://localhost:6001/orders/id/{orderId}";

        ResponseEntity<OrderFromOrderServiceResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                OrderFromOrderServiceResponse.class,
                OrderId
        );

        return response.getBody().getTotalPrice();


    }

}

