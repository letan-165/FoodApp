package com.app.AccountService.Mapper;


import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.DTO.Response.UserResponse;
import com.app.AccountService.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper{
    @Mapping(target = "role",ignore = true)
    User toUser(UserRequest request);
    @Mapping(target = "role",ignore = true)
    UserResponse toUserResponse(User user);
}
