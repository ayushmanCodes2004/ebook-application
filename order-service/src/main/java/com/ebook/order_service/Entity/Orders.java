package com.ebook.order_service.Entity;

import com.ebook.order_service.DTO.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    private String orderId;
    private String customerId;
    private LocalDateTime orderDate;
    private Double totalPrice;

    @Enumerated(value = jakarta.persistence.EnumType.STRING)
    private OrderStatus status;


}
