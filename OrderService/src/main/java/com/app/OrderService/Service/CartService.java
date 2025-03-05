package com.app.OrderService.Service;

import com.app.OrderService.DTO.Response.CartResponse;
import com.app.OrderService.Entity.Cart;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.CartMapper;
import com.app.OrderService.Model.ItemModel;
import com.app.OrderService.Repository.CartRepository;
import com.app.OrderService.Repository.HttpClient.UserClient;
import com.app.OrderService.Repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    UserClient userClient;
    CartMapper cartMapper;
    public List<CartResponse> findAll(){
        var carts = cartRepository.findAll();
        return carts.stream().map(cartMapper::toCartResponse).toList();
    }

    public CartResponse save(String userID){
        userClient.findById(userID);

        if(cartRepository.existsById(userID)){
            throw new AppException(ErrorCode.CART_EXISTS);
        }

        return cartMapper.toCartResponse(cartRepository.save(Cart.builder()
                .userID(userID)
                        .restaurants(new HashMap<>())
                .build()));
    }

    public CartResponse findById(String cartID){
        Cart cart = cartRepository.findById(cartID)
                .orElseThrow(()->new AppException(ErrorCode.CART_NO_EXISTS));

        return cartMapper.toCartResponse(cart);
    }

    public Boolean delete(String cartID){
        if(!cartRepository.existsById(cartID)){
            throw new AppException(ErrorCode.CART_NO_EXISTS);
        }
        cartRepository.deleteById(cartID);
        return true;
    }

    RestaurantRepository restaurantRepository;
    public ItemModel addItem(String cartID,String restaurantID,ItemModel request){
        //check cart exists
        Cart cart = cartRepository.findById(cartID)
                .orElseThrow(()->new AppException(ErrorCode.CART_NO_EXISTS));
        //check restaurant exists
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));
        //Give list item of restaurant from menu
        ItemModel itemRestaurant = restaurant.getMenu().get(request.getItemID());
        //check item on menu of restaurant
        if(itemRestaurant==null){
            throw new AppException(ErrorCode.ITEM_NO_EXISTS);
        }
        request.setPrice(itemRestaurant.getPrice());
        request.setName(itemRestaurant.getName());

        //Give restaurant of cart
        Restaurant cartRestaurant = cart.getRestaurants().getOrDefault(restaurantID,Restaurant.builder()
                        .restaurantID(restaurantID)
                        .name(restaurant.getName())
                        .menu(new HashMap<>())
                .build());

        //Add or update new item from request in restaurant
        cartRestaurant.getMenu().put(request.getItemID(),request);
        //Update new restaurant on cart
        cart.getRestaurants().put(restaurantID,cartRestaurant);

        cartRepository.save(cart);
        return request;
    }

    public Boolean deleteID(String cartID,String restaurantID,Long itemID){
        //check cart exists
        Cart cart = cartRepository.findById(cartID)
                .orElseThrow(()->new AppException(ErrorCode.CART_NO_EXISTS));
        //check restaurant exists
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));
        //check item on menu of restaurant
        if(restaurant.getMenu().get(itemID)==null){
            throw new AppException(ErrorCode.ITEM_NO_EXISTS);
        }
        //Give restaurant of cart
        Restaurant cartRestaurant = cart.getRestaurants().get(restaurantID);
        //Add or update new item from request in restaurant
        cartRestaurant.getMenu().remove(itemID);
        //Update new restaurant on cart
        cart.getRestaurants().put(restaurantID,cartRestaurant);

        cartRepository.save(cart);
        return true;
    }


}
