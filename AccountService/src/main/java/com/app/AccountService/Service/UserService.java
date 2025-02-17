package com.app.AccountService.Service;

import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.DTO.Response.UserResponse;
import com.app.AccountService.Entity.Role;
import com.app.AccountService.Entity.User;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Exception.ErrorCode;
import com.app.AccountService.Mapper.UserMapper;
import com.app.AccountService.Repository.RoleRepository;
import com.app.AccountService.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    UserResponse toUserResponse(String userID,UserRequest request){
        User user = userMapper.toUser(request);
        if(!userID.isEmpty()){
            user.setUserID(userID);
        }
        Role role = roleRepository.findById(request.getRole()).orElseThrow(()
                ->new AppException(ErrorCode.ROLE_NO_EXISTS));

        user.setRole(role);
        UserResponse userResponse = userMapper.toUserResponse(userRepository.save(user));
        userResponse.setRole(role.getName());
        return userResponse;
    }

    public UserResponse save(UserRequest request){
        if (userRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        return toUserResponse("",request);
    }

    public UserResponse update(String userID,UserRequest request){
        User user = userRepository.findById(userID)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NO_EXISTS));

        if (request.getPassword()==null){
            request.setPassword(user.getPassword());
        }

        return toUserResponse(userID, request);
    }

    public UserResponse findById(String userID){
        return userMapper.toUserResponse(userRepository.findById(userID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NO_EXISTS)));
    }
    public UserResponse findByName(String name){
        return userMapper.toUserResponse(userRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NO_EXISTS)));
    }

    public Boolean delete(String userID){
        try{
            userRepository.deleteById(userID);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.USER_NO_EXISTS);
        }
    }



}
