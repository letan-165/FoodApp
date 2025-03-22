package com.app.AccountService.Config;

import com.app.AccountService.Entity.Permission;
import com.app.AccountService.Entity.Role;
import com.app.AccountService.Entity.User;
import com.app.AccountService.Repository.HttpClient.CartClient;
import com.app.AccountService.Repository.PermissionRepository;
import com.app.AccountService.Repository.RoleRepository;
import com.app.AccountService.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AppRunner {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    CartClient cartClient;


    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            List<Permission> permissions = new ArrayList<>(List.of(
                    new Permission("ADD", "add"),
                    new Permission("UPDATE", "update"),
                    new Permission("DELETE", "delete"),
                    new Permission("READ", "read")
            ));
            List<Role> roles = new ArrayList<>(List.of(
                    new Role("ADMIN", "admin", permissions),
                    new Role("SHIPPER", "admin", Collections.emptyList()),
                    new Role("CUSTOMER", "admin", Collections.emptyList()),
                    new Role("RESTAURANT", "admin", Collections.emptyList())
            ));
            if (!permissionRepository.existsById(permissions.get(0).getName())) {
                permissionRepository.saveAll(permissions);
            }
            if (!roleRepository.existsById(roles.get(0).getName())) {
                roleRepository.saveAll(roles);
            }

            if (!userRepository.existsByName("tan1")) {
                User user = userRepository.save(User.builder()
                                .name("tan1")
                                .gmail("tan@1")
                                .password(passwordEncoder.encode("1"))
                                .roles(roles)
                                .phone("0102")
                        .build());
                cartClient.save(user.getUserID());
            }
        };
    }

}
