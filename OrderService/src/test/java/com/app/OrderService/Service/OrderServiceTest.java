package com.app.OrderService.Service;

import com.app.OrderService.DTO.Request.Order.OrderSaveRequest;
import com.app.OrderService.DTO.Response.OrderResponse;
import com.app.OrderService.DTOMock.EntityMock;
import com.app.OrderService.DTOMock.RequestMock;
import com.app.OrderService.DTOMock.ResponseMock;
import com.app.OrderService.Entity.Cart;
import com.app.OrderService.Entity.Order;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.OrderMapper;
import com.app.OrderService.Repository.CartRepository;
import com.app.OrderService.Repository.HttpClient.UserClient;
import com.app.OrderService.Repository.OrderRepository;
import com.app.OrderService.Repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderServiceTest {
    @InjectMocks OrderService orderService;

    @Mock OrderRepository orderRepository;
    @Mock RestaurantRepository restaurantRepository;
    @Mock OrderMapper orderMapper;
    @Mock UserClient userClient;
    @Mock CartRepository cartRepository;

    Order order;
    Cart cart;
    Restaurant restaurant;
    OrderResponse orderResponse;
    OrderSaveRequest orderSaveRequest;

    @BeforeEach
    void initData() {
        order = EntityMock.order();
        cart = EntityMock.cart();
        restaurant = EntityMock.restaurantMock();
        orderResponse = ResponseMock.orderResponse();
        orderSaveRequest = RequestMock.orderSaveRequest();
    }

    @Test
    void save_success(){
        when(restaurantRepository.findById(eq(orderSaveRequest.getRestaurantID())))
                .thenReturn(Optional.of(restaurant));
        when(orderMapper.toOrder(orderSaveRequest)).thenReturn(order);
        when(orderRepository.save(any())).thenReturn(order);
        when(orderMapper.toOrderResponse(order)).thenReturn(orderResponse);


        OrderResponse response = orderService.save(orderSaveRequest,false);
        verify(userClient).findById(eq(orderSaveRequest.getCustomerID()));
        assertThat(response).isEqualTo(orderResponse);
    }

    @Test
    void saveFromCart_success(){
        when(restaurantRepository.findById(eq(orderSaveRequest.getRestaurantID())))
                .thenReturn(Optional.of(restaurant));
        when(cartRepository.findById(eq(orderSaveRequest.getCustomerID()))).thenReturn(Optional.of(cart));
        when(orderMapper.toOrder(orderSaveRequest)).thenReturn(order);
        when(orderRepository.save(any())).thenReturn(order);
        when(orderMapper.toOrderResponse(order)).thenReturn(orderResponse);

        OrderResponse response = orderService.save(orderSaveRequest,true);
        verify(userClient).findById(eq(orderSaveRequest.getCustomerID()));
        verify(cartRepository).save(cart);


        assertThat(response).isEqualTo(orderResponse);
    }

    @Test
    void saveFromCart_fail_noFindRestaurantOnCart(){
        orderSaveRequest.setRestaurantID("noFind");
        when(restaurantRepository.findById(eq(orderSaveRequest.getRestaurantID())))
                .thenReturn(Optional.of(restaurant));
        when(cartRepository.findById(eq(orderSaveRequest.getCustomerID()))).thenReturn(Optional.of(cart));

        var exception = assertThrows(RuntimeException.class,
                ()->orderService.save(orderSaveRequest,true));

        assertThat(exception.getMessage()).isEqualTo("Cửa hàng không tồn tại trong giỏ hàng");

    }

    @Test
    void saveFromCart_fail_noFindItemOnCart(){
        var menu = orderSaveRequest.getMenu();
        menu.get(1).setItemID(3L);
        orderSaveRequest.setMenu(menu);
        when(restaurantRepository.findById(eq(orderSaveRequest.getRestaurantID())))
                .thenReturn(Optional.of(restaurant));
        when(cartRepository.findById(eq(orderSaveRequest.getCustomerID()))).thenReturn(Optional.of(cart));

        var exception = assertThrows(RuntimeException.class,
                ()->orderService.save(orderSaveRequest,true));

        assertThat(exception.getMessage()).isEqualTo("Món ăn không tồn tại trong giỏ hàng");

    }


}
