package com.app.OrderService.Service;

import com.app.OrderService.DTO.BaseDTO.ItemCartEntity;
import com.app.OrderService.DTO.BaseDTO.RestaurantCartEntity;
import com.app.OrderService.DTO.Request.Cart.ItemCartRequest;
import com.app.OrderService.DTO.Response.Cart.RestaurantCartResponse;
import com.app.OrderService.DTO.Response.Cart.CartResponse;
import com.app.OrderService.Entity.Cart;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.CartMapper;
import com.app.OrderService.Repository.CartRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CartService {
    CartRepository cartRepository;
    CartMapper cartMapper;

    public List<CartResponse> findAll(){
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(cartMapper::toCartResponse).toList();
    }

    public CartResponse findById(String cartID){
        Cart cart = cartRepository.findById(cartID)
                .orElseThrow(()->new AppException(ErrorCode.CART_NO_EXISTS));

        return cartMapper.toCartResponse(cart);
    }

    public CartResponse save(String cartID){
        if(cartRepository.existsById(cartID))
            throw new AppException(ErrorCode.CART_EXISTS);

        return cartMapper.toCartResponse(cartRepository.save(Cart.builder()
                        .userID(cartID)
                        .restaurants(Collections.emptyMap())
                .build()));
    }

    public Boolean delete(String cartID){
        if(!cartRepository.existsById(cartID))
            throw new AppException(ErrorCode.CART_NO_EXISTS);
        try{
            cartRepository.deleteById(cartID);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Xóa thất bại");
        }
    }

    ItemService itemService;

    public RestaurantCartResponse addItem(ItemCartRequest request){
        Cart cart = cartRepository.findById(request.getCartID())
                .orElseThrow(()->new AppException(ErrorCode.CART_NO_EXISTS));

        itemService.findItem(request.getRestaurantID(), request.getItemID());


        RestaurantCartEntity restaurantCart = cart.getRestaurants().get(request.getRestaurantID());
        if (restaurantCart==null)
            restaurantCart = RestaurantCartEntity.builder()
                    .restaurantID(request.getRestaurantID())
                    .build();

        restaurantCart.getMenu().get(request.getItemID());

        ItemCartEntity itemCart = restaurantCart.getMenu()
                .putIfAbsent(request.getItemID(), ItemCartEntity.builder()
                        .itemID(request.getItemID())
                        .quantity(1L)
                    .build());

        if(itemCart!=null)
            throw new RuntimeException("item đã tồn tại trong giỏ hàng");

        cart.getRestaurants().put(restaurantCart.getRestaurantID(),restaurantCart);

        try{
            cartRepository.save(cart);
            return cartMapper.toRestaurantCartResponse( restaurantCart,request.getRestaurantID());
        }catch (Exception e){
            throw new RuntimeException("Thêm item không thành công");
        }
    }

    public Boolean deleteItem(ItemCartRequest request){
        Cart cart = cartRepository.findById(request.getCartID())
                .orElseThrow(()->new AppException(ErrorCode.CART_NO_EXISTS));

        itemService.findItem(request.getRestaurantID(), request.getItemID());

        RestaurantCartEntity restaurantCart = cart.getRestaurants().get(request.getRestaurantID());
        if (restaurantCart==null)
            throw new RuntimeException("Cửa hàng không tồn tại trong giỏ hàng");

        ItemCartEntity itemCart = restaurantCart.getMenu().remove(request.getItemID());
        if (itemCart==null)
            throw new RuntimeException("Món ăn không có trong giỏ hàng");


        try{
            cartRepository.save(cart);
            return true;
        }catch (Exception e){
            throw new RuntimeException("Thêm item không thành công");
        }
    }



}
