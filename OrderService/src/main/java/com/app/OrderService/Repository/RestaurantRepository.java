package com.app.OrderService.Repository;

import com.app.OrderService.Entity.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant,String> {
    List<Restaurant> findAllByUserID(String userID);
}
