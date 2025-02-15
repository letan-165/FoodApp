package com.app.OrderService.Repository;

import com.app.OrderService.Entity.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository<Restaurant,Long> {
}
