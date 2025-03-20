package com.app.AccountService.Mapper;


import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.DTO.Response.UserResponse;
import com.app.AccountService.Entity.Role;
import com.app.AccountService.Entity.User;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Exception.ErrorCode;
import com.app.AccountService.Repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper{
    @Autowired
    RoleRepository roleRepository;

    @Mapping(target = "roles",source = "roles", qualifiedByName = "mapToListRole")
    public abstract User toUser(UserRequest request);

    @Mapping(target = "roles",source = "roles", qualifiedByName = "mapToListString")
    public abstract UserResponse toUserResponse(User user);

    @Named("mapToListRole")
    public List<Role> mapToListRole(List<String> roles){
        return roleRepository.findAllById(roles);
    }

    @Named("mapToListString")
    public List<String> mapToListString(List<Role> roles){
        return roles.stream().map(Role::getName).toList();
    }

}
