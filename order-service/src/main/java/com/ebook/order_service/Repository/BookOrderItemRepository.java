package com.ebook.order_service.Repository;

import com.ebook.order_service.DTO.OrderResponseDTO;
import com.ebook.order_service.Entity.BookOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookOrderItemRepository extends JpaRepository<BookOrderItem, String> {

    List<BookOrderItem> findByOrderId(String orderId);

}
