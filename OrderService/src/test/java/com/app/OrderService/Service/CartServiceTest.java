package com.app.OrderService.Service;

import com.app.OrderService.DTO.Request.Cart.ItemCartRequest;
import com.app.OrderService.DTO.Response.Cart.RestaurantCartResponse;
import com.app.OrderService.DTOMock.EntityMock;
import com.app.OrderService.DTOMock.RequestMock;
import com.app.OrderService.DTOMock.ResponseMock;
import com.app.OrderService.Entity.Cart;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.CartMapper;
import com.app.OrderService.Repository.CartRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartServiceTest {
    @InjectMocks CartService cartService;
    @Mock CartRepository cartRepository;
    @Mock CartMapper cartMapper;
    @Mock ItemService itemService;

    ItemCartRequest itemCartRequest;
    RestaurantCartResponse restaurantCartResponse;
    Cart cart;
    String cartID;

    @BeforeEach
    void initData(){
        itemCartRequest = RequestMock.itemCartRequest(1L);
        restaurantCartResponse = ResponseMock.restaurantCartResponse();
        cart = EntityMock.cart();
        cartID = itemCartRequest.getCartID();
    }

    @Test
    void addItem_success(){
        itemCartRequest.setItemID(3L);
        when(cartRepository.findById(cartID)).thenReturn(Optional.ofNullable(cart));
        when(cartMapper.toRestaurantCartResponse(any(),anyString())).thenReturn(restaurantCartResponse);
        RestaurantCartResponse response = cartService.addItem(itemCartRequest);

        verify(itemService).findItem(eq(itemCartRequest.getRestaurantID()), eq(itemCartRequest.getItemID()));
        verify(cartRepository).save(eq(cart));

        assertThat(response).isEqualTo(restaurantCartResponse);
    }

    @Test
    void addItem_fail_noFindCart(){
        when(cartRepository.findById(cartID)).thenReturn(Optional.empty());

        var exception = assertThrows(AppException.class, ()-> cartService.addItem(itemCartRequest));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.CART_NO_EXISTS);
    }

    @Test
    void addItem_fail_itemExists(){
        when(cartRepository.findById(cartID)).thenReturn(Optional.ofNullable(cart));

        var exception = assertThrows(RuntimeException.class, ()-> cartService.addItem(itemCartRequest));
        assertThat(exception.getMessage()).isEqualTo("item đã tồn tại trong giỏ hàng");
    }

    @Test
    void deleteItem_success(){
        when(cartRepository.findById(cartID)).thenReturn(Optional.ofNullable(cart));

        boolean response = cartService.deleteItem(itemCartRequest);

        verify(itemService).findItem(eq(itemCartRequest.getRestaurantID()), eq(itemCartRequest.getItemID()));
        verify(cartRepository).save(cart);

        assertThat(response).isTrue();
    }

    @Test
    void deleteItem_fail_noFindCart(){
        when(cartRepository.findById(cartID)).thenReturn(Optional.empty());

        var exception = assertThrows(AppException.class, ()-> cartService.addItem(itemCartRequest));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.CART_NO_EXISTS);
    }

    @Test
    void deleteItem_fail_noExistRestaurantOnCart(){
        itemCartRequest.setRestaurantID("noFind");
        when(cartRepository.findById(cartID)).thenReturn(Optional.ofNullable(cart));

        var exception = assertThrows(RuntimeException.class,()->cartService.deleteItem(itemCartRequest));

        assertThat(exception.getMessage()).isEqualTo("Cửa hàng không tồn tại trong giỏ hàng");
    }

    @Test
    void deleteItem_fail_noExistItemOnCart(){
        itemCartRequest.setItemID(3L);
        when(cartRepository.findById(cartID)).thenReturn(Optional.ofNullable(cart));

        var exception = assertThrows(RuntimeException.class,()->cartService.deleteItem(itemCartRequest));

        assertThat(exception.getMessage()).isEqualTo("Món ăn không có trong giỏ hàng");
    }

}
