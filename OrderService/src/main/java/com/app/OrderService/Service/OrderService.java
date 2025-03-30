package com.app.OrderService.Service;

import com.app.OrderService.DTO.BaseDTO.ItemCartEntity;
import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import com.app.OrderService.DTO.BaseDTO.RestaurantCartEntity;
import com.app.OrderService.DTO.Request.Order.OrderSaveRequest;
import com.app.OrderService.DTO.Request.Order.OrderUpdateReQuest;
import com.app.OrderService.DTO.Request.Order.OrderUpdateStatusRequest;
import com.app.OrderService.DTO.Response.OrderResponse;
import com.app.OrderService.Entity.Cart;
import com.app.OrderService.Entity.Order;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Enum.OrderStatus;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.OrderMapper;
import com.app.OrderService.Repository.CartRepository;
import com.app.OrderService.Repository.HttpClient.UserClient;
import com.app.OrderService.Repository.OrderRepository;
import com.app.OrderService.Repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class OrderService {
    OrderRepository orderRepository;
    RestaurantRepository restaurantRepository;
    OrderMapper orderMapper;
    UserClient userClient;
    CartRepository cartRepository;

    public List<OrderResponse> findAll(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map((orderMapper::toOrderResponse)).toList();
    }

    public List<OrderResponse> findAllByUser(String userID){
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .filter(order -> order.getCustomerID().equals(userID))
                .map((orderMapper::toOrderResponse)).toList();
    }

    public List<OrderResponse> findAllByRestaurant(String restaurantID){
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .filter(order -> order.getRestaurantID().equals(restaurantID))
                .map((orderMapper::toOrderResponse)).toList();
    }

    public List<OrderResponse> findAllByShipper(String shipperID){
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .filter(order -> order.getShipperID()!=null && order.getShipperID().equals(shipperID))
                .map((orderMapper::toOrderResponse)).toList();
    }

    public List<OrderResponse> findAllStatus(String status){
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.valueOf(status)))
                .map((orderMapper::toOrderResponse)).toList();
    }



    public OrderResponse save(OrderSaveRequest request,boolean fromCart){
        userClient.findById(request.getCustomerID());
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantID())
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));
        Cart cart = null;
        if(fromCart){
            cart = cartRepository.findById(request.getCustomerID())
                    .orElseThrow(()->new AppException(ErrorCode.CART_NO_EXISTS));

            RestaurantCartEntity restaurantCart = cart.getRestaurants().get(request.getRestaurantID());
            if(restaurantCart==null)
                throw new RuntimeException("Cửa hàng không tồn tại trong giỏ hàng");

            request.getMenu().forEach(item->{
                        ItemCartEntity itemCart = restaurantCart.getMenu().remove(item.getItemID());
                        if(itemCart==null)
                            throw new RuntimeException("Món ăn không tồn tại trong giỏ hàng");
                    });
            cart.getRestaurants().put(restaurantCart.getRestaurantID(),restaurantCart);
        }

        Map<Long,ItemEntity> menuOrder = menuOrder(request.getMenu(),restaurant);
        Long total = total(menuOrder);

        Order order = orderMapper.toOrder(request).toBuilder()
                .menu(menuOrder)
                .total(total)
                .time(Instant.now())
                .status(OrderStatus.PENDING)
                .build();

        OrderResponse orderResponse = orderMapper.toOrderResponse(orderRepository.save(order));
        if(orderResponse != null && fromCart){
            cartRepository.save(cart);
        }


        return orderResponse;
    }

    public OrderResponse update(String orderID,OrderUpdateReQuest request){
        if(!orderRepository.existsById(orderID))
            throw new AppException(ErrorCode.ORDER_NO_EXISTS);
        userClient.findById(request.getCustomerID());
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantID())
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));
        if(request.getShipperID()!=null)
            userClient.findById(request.getShipperID());

        Map<Long,ItemEntity> menuOrder = menuOrder(request.getMenu(),restaurant);
        Long total = total(menuOrder);


        Order order = orderMapper.toOrder(request);
        Order orderUpdate =order.toBuilder()
                .orderID(orderID)
                .shipperID(request.getShipperID())
                .menu(menuOrder)
                .total(total)
                .time(Instant.now())
                .status(request.getStatus()!=null ? request.getStatus() : order.getStatus())
                .build();

        return orderMapper.toOrderResponse(orderRepository.save(orderUpdate));
    }


    public OrderResponse findById(String orderID){
        Order order = orderRepository.findById(orderID)
                .orElseThrow(()->new AppException(ErrorCode.ORDER_EXISTS));

        return orderMapper.toOrderResponse(order);
    }
    public Boolean delete(String orderID){
        if(!orderRepository.existsById(orderID))
            throw new AppException(ErrorCode.ORDER_NO_EXISTS);
        try{
            orderRepository.deleteById(orderID);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Xóa thất bại");
        }
    }



    public Map<Long,ItemEntity> menuOrder (List<ItemCartEntity> menu,Restaurant restaurant) {
        return menu.stream()
                .map(itemCart->{
                    ItemEntity item = restaurant.getMenu().get(itemCart.getItemID());
                    if(item==null)
                        throw new AppException(ErrorCode.ITEM_NO_EXISTS);

                    item.setQuantity(itemCart.getQuantity());
                    return item;
                })
                .collect(Collectors.toMap(ItemEntity::getItemID,itemEntity -> itemEntity));
    }

    public Long total(Map<Long,ItemEntity>menuOrder){
        return menuOrder.values().stream()
                .mapToLong(item ->item.getPrice() * item.getQuantity())
                .sum();
    }


    public OrderResponse updateStatus(String orderID,OrderUpdateStatusRequest request){
        Order order = orderRepository.findById(orderID)
                .orElseThrow(()->new AppException(ErrorCode.ORDER_NO_EXISTS));
        if(request.getShipperID() !=null){
            userClient.findById(request.getShipperID());
            order.setShipperID(request.getShipperID());
        }
        order.setStatus(request.getStatus());

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }



}
