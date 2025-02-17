package com.app.AccountService.Repository;

import com.app.AccountService.Entity.Logout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoutRepository extends JpaRepository<Logout,String> {
}
