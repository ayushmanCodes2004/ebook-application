package com.ebook.payment_service.Repository;

import com.ebook.payment_service.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    Payment findByOrderId(String orderId);

}
