package com.app.OrderService.Mapper;

import com.app.OrderService.DTO.Request.Order.OrderSaveRequest;
import com.app.OrderService.DTO.Response.OrderResponse;
import com.app.OrderService.Entity.Order;
import com.app.OrderService.Mapper.CustomMapper.CustomMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = CustomMapper.class)
public interface OrderMapper {
    @Mapping(target = "menu", source = "menu", qualifiedByName = "mapToListMenu")
    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "menu",ignore = true)
    Order toOrder(OrderSaveRequest request);
}
