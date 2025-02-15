package com.app.OrderService.Repository;

import com.app.OrderService.Entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment,Long> {
}
