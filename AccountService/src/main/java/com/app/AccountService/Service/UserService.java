package com.app.AccountService.Service;

import com.app.AccountService.DTO.Request.CartRequest;
import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.DTO.Response.UserResponse;
import com.app.AccountService.Entity.Role;
import com.app.AccountService.Entity.User;
import com.app.AccountService.Enum.RoleEnum;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Exception.ErrorCode;
import com.app.AccountService.Mapper.UserMapper;
import com.app.AccountService.Repository.HttpClient.CartClient;
import com.app.AccountService.Repository.RoleRepository;
import com.app.AccountService.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    CartClient cartClient;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public UserResponse save(UserRequest request){
        if (userRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.USER_EXISTS);
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.getRoles().add(RoleEnum.CUSTOMER.getName());
        UserResponse userResponse = userMapper.toUserResponse(
                userRepository.save(userMapper.toUser(request)));
        try{
            cartClient.save(CartRequest.builder()
                    .userID(userResponse.getUserID())
                    .build());
        }catch (Exception e){
            throw new RuntimeException("False create cart");
        }

        return userResponse;
    }

    public UserResponse update(String userID,UserRequest request){
        User user = userRepository.findById(userID)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NO_EXISTS));

        Optional<User> checkUserName = userRepository.findByName(request.getName());

        if(checkUserName.isPresent() && !userID.equals(checkUserName.get().getUserID()))
            throw new AppException(ErrorCode.USER_NAME_EXISTS);

        request.setPassword((request.getPassword()==null)
                ? user.getPassword()
                : passwordEncoder.encode(request.getPassword()));

        User userUpdate = userMapper.toUser(request);
        userUpdate.setUserID(userID);

        return userMapper.toUserResponse(
                userRepository.save(userUpdate));
    }

    public UserResponse findById(String userID){
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NO_EXISTS));

        return userMapper.toUserResponse(user);
    }
    public UserResponse findByName(String name){
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NO_EXISTS));

        return userMapper.toUserResponse(user);
    }

    public Boolean delete(String userID){
        try{
            boolean deleteCart =
                    cartClient.delete(userID).getResult();
            if(deleteCart)
                userRepository.deleteById(userID);

            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.USER_NO_EXISTS);
        }
    }



}
