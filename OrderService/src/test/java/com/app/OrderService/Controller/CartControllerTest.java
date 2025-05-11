package com.app.OrderService.Controller;

import com.app.OrderService.DTO.Request.Cart.ItemCartRequest;
import com.app.OrderService.DTO.Response.Cart.CartResponse;
import com.app.OrderService.DTO.Response.Cart.RestaurantCartResponse;
import com.app.OrderService.DTOMock.RequestMock;
import com.app.OrderService.DTOMock.ResponseMock;
import com.app.OrderService.Service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "KEY_JWT=1234")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CartService cartService;

    ItemCartRequest itemCartRequest;
    RestaurantCartResponse restaurantCartResponse;
    CartResponse cartResponse;

    @BeforeEach
    void initData(){
        itemCartRequest = RequestMock.itemCartRequest(1L);
        restaurantCartResponse = ResponseMock.restaurantCartResponse();
    }

    @Test
    void findAll_success() throws Exception {
        when(cartService.findAll()).thenReturn(new ArrayList<>(List.of(new CartResponse(),new CartResponse())));
        mockMvc.perform(get("/cart")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))
                .andExpect(jsonPath("result.length()").value(2));
    }

    void addItem_success() throws Exception {
        when(cartService.addItem(itemCartRequest)).thenReturn(restaurantCartResponse);
        var content = objectMapper.writeValueAsString(itemCartRequest);

        mockMvc.perform(put("/cart/item/add").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))
                .andExpect(jsonPath("result.restaurantID").value(restaurantCartResponse.getRestaurantID()));

    }

    void deleteItem_success() throws Exception {
        when(cartService.deleteItem(itemCartRequest)).thenReturn(true);
        var content = objectMapper.writeValueAsString(itemCartRequest);

        mockMvc.perform(put("/cart/item/delete").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))
                .andExpect(jsonPath("result").value(true));

    }



}
