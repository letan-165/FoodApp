package com.app.OrderService.Mapper;

import com.app.OrderService.DTO.Request.OrderRequest;
import com.app.OrderService.DTO.Response.OrderResponse;
import com.app.OrderService.Entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface OrderMapper {
    @Mapping(target = "menu", source = "menu", qualifiedByName = "mapToMap" )
    Order toOrder(OrderRequest request);


    @Mapping(target = "menu", source = "menu", qualifiedByName = "mapToList" )
    OrderResponse toOrderResponse(Order order);
}
