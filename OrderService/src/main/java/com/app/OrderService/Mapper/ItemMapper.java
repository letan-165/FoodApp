package com.app.OrderService.Mapper;

import com.app.OrderService.DTO.Request.Restaurant.ItemEditRequest;
import com.app.OrderService.DTO.Response.Restaurant.ItemRestaurantResponse;
import com.app.OrderService.Mapper.CustomMapper.CustomMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = CustomMapper.class)
public interface ItemMapper {

    ItemRestaurantResponse toItemRestaurantResponse(ItemEditRequest request);
}
