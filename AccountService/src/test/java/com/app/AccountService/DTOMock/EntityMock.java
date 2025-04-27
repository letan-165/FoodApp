package com.app.AccountService.DTOMock;

import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.Entity.Permission;
import com.app.AccountService.Entity.Role;
import com.app.AccountService.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class EntityMock {
    public static User userMock(){
        String[] permissionName = {"ADD", "DELETE","UPDATE","READ"};
        ArrayList<Permission> permissions = new ArrayList<>();
        for(String n : permissionName){
            permissions.add(Permission.builder()
                            .name(n)
                            .description(n)
                    .build());
        }

        Role roleAdmin = Role.builder()
                .name("ADMIN")
                .description("ADMIN")
                .permissions(permissions)
                .build();

        Role roleCustomer = Role.builder()
                .name("CUSTOMER")
                .description("CUSTOMER")
                .permissions(List.of(permissions.get(3)))
                .build();


        return User.builder()
                .userID("userID")
                .name("name")
                .phone("phone")
                .gmail("gmail")
                .roles(List.of(roleAdmin,roleCustomer))
                .build();
    }
}
