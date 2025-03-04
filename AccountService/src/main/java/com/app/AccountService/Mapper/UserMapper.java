package com.app.AccountService.Mapper;


import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.DTO.Response.UserResponse;
import com.app.AccountService.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper{
    @Mapping(target = "roles",ignore = true)
    User toUser(UserRequest request);

    @Mapping(target = "roles",ignore = true)
    UserResponse toUserResponse(User user);
}
