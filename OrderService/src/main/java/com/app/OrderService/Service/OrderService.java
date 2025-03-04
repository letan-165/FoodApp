package com.app.OrderService.Service;

import com.app.OrderService.DTO.Request.OrderRequest;
import com.app.OrderService.DTO.Response.OrderResponse;
import com.app.OrderService.DTO.Response.UserResponse;
import com.app.OrderService.Entity.Order;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Enum.OrderStatus;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.OrderMapper;
import com.app.OrderService.Model.ItemModel;
import com.app.OrderService.Repository.HttpClient.UserClient;
import com.app.OrderService.Repository.OrderRepository;
import com.app.OrderService.Repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    RestaurantRepository restaurantRepository;
    UserClient userClient;

    public List<OrderResponse> findAll(){
        var orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    public OrderResponse save(String orderID,OrderRequest request){
        Order order = orderMapper.toOrder(request);
        if(!orderID.isEmpty()) {
            if(orderRepository.existsById(orderID)){
                order.setOrderID(orderID);
            }else{
                throw new AppException(ErrorCode.ORDER_NO_EXISTS);
            }
        }
        userClient.findById(request.getCustomerID());
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantID())
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        order.getMenu().values().forEach(itemModel -> {
            ItemModel itemRestaurant = restaurant.getMenu().get(itemModel.getItemID());
            if(itemRestaurant==null){
                throw new AppException(ErrorCode.ITEM_NO_EXISTS);
            }
            itemModel.setName(itemRestaurant.getName());
            itemModel.setPrice(itemRestaurant.getPrice());
        });
        order.setTotal(order.getMenu().values().stream().mapToLong(ItemModel::getPrice).sum());
        order.setTime(LocalDateTime.now());
        order.setStatus((order.getOrderID()==null)? OrderStatus.PENDING : OrderStatus.valueOf(request.getStatus()));


        return orderMapper.toOrderResponse(
                orderRepository.save(order));
    }

    public OrderResponse findById(String orderID){
        return orderMapper.toOrderResponse(
                orderRepository.findById(orderID)
                        .orElseThrow(()->new AppException(ErrorCode.ORDER_NO_EXISTS)));
    }


    public Boolean delete(String orderID){
        try {
            orderRepository.deleteById(orderID);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.ORDER_NO_EXISTS);
        }
    }



}
