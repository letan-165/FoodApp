package com.app.AccountService.Repository;

import com.app.AccountService.Entity.Logout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogoutRepository extends JpaRepository<Logout,String> {
}
