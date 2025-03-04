package com.app.AccountService.Service;

import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.DTO.Response.UserResponse;
import com.app.AccountService.Entity.Role;
import com.app.AccountService.Entity.User;
import com.app.AccountService.Enum.RoleEnum;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Exception.ErrorCode;
import com.app.AccountService.Mapper.UserMapper;
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
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public List<User> findAll(){
        return userRepository.findAll();
    }

    UserResponse toUserResponse(String userID,UserRequest request){
        User user = userMapper.toUser(request);
        if(CollectionUtils.isEmpty(request.getRoles())){
            request.getRoles().add(RoleEnum.CUSTOMER.getName());
        }
        if(!userID.isEmpty()){
            user.setUserID(userID);
        }
        List<Role> roles =  roleRepository.findAllById(request.getRoles());

        user.setRoles(roles);
        UserResponse userResponse = userMapper.toUserResponse(userRepository.save(user));
        userResponse.setRoles(request.getRoles());
        return userResponse;
    }

    public UserResponse save(UserRequest request){
        if (userRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.USER_EXISTS);
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return toUserResponse("",request);
    }

    public UserResponse update(String userID,UserRequest request){
        User user = userRepository.findById(userID)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NO_EXISTS));

        Optional<User> checkUserName = userRepository.findByName(request.getName());

        if(checkUserName.isPresent() && !userID.equals(checkUserName.get().getUserID())){
            throw new AppException(ErrorCode.USER_NAME_EXISTS);
        }

        if (request.getPassword()==null){
            request.setPassword(user.getPassword());
        }else{
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return toUserResponse(userID, request);
    }

    public UserResponse findById(String userID){
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NO_EXISTS));

        UserResponse userResponse =  userMapper.toUserResponse(user);
        userResponse.setRoles(user.getRoles().stream().map(Role::getName).toList());

        return userResponse;
    }
    public UserResponse findByName(String name){
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NO_EXISTS));

        UserResponse userResponse =  userMapper.toUserResponse(user);
        userResponse.setRoles(user.getRoles().stream().map(Role::getName).toList());
        return userResponse;
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
