package com.ebook.order_service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookOrderItem {

    @Id
    private String orderItemId;
    private String orderId;
    private String bookId;
    private Integer quantity;
    private Double price;

}
