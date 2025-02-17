package com.app.OrderService.Mapper;

import com.app.OrderService.DTO.Request.OrderRequest;
import com.app.OrderService.DTO.Response.OrderResponse;
import com.app.OrderService.Entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequest request);
    OrderResponse toOrderResponse(Order order);
}
