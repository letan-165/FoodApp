package com.app.AccountService.Service;

import com.app.AccountService.Entity.Logout;
import com.app.AccountService.Repository.LogoutRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LogoutService {
    LogoutRepository logoutRepository;

    public List<Logout> findAll(){
        return logoutRepository.findAll();
    }

    public Logout save(Logout logout){
        return logoutRepository.save(logout);
    }
    
    public boolean exists(String token){
        return logoutRepository.existsById(token);
    }


}
