package com.app.OrderService.Service;

import com.app.OrderService.DTO.Request.OrderRequest;
import com.app.OrderService.DTO.Response.OrderResponse;
import com.app.OrderService.Entity.Order;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.OrderMapper;
import com.app.OrderService.Repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public OrderResponse save(OrderRequest request){
        //check userID
        return orderMapper.toOrderResponse(
                orderRepository.save(
                        orderMapper.toOrder(request)));
    }

    public OrderResponse findById(String orderID){
        //check userID
        return orderMapper.toOrderResponse(
                orderRepository.findById(orderID)
                        .orElseThrow(()->new AppException(ErrorCode.ORDER_NO_EXISTS)));
    }

    public OrderResponse update(String orderID, OrderRequest request) {
        //check userID
        //check orderID
        if (!orderRepository.existsById(orderID)) {
            throw new AppException(ErrorCode.ORDER_NO_EXISTS);
        }
        //Set ID
        Order order = orderMapper.toOrder(request);
        order.setOrderID(orderID);
        return orderMapper.toOrderResponse(
                orderRepository.save(order));
    }

    public Boolean delete(String orderID){
        //check userID
        try {
            orderRepository.deleteById(orderID);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.ORDER_NO_EXISTS);
        }
    }



}
