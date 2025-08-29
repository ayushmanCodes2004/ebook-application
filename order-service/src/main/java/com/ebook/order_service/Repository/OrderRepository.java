package com.ebook.order_service.Repository;

import com.ebook.order_service.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {

    List<Orders> findByCustomerId(String customerId);

}
