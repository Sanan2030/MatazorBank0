package com.programing.MatazorBank.Repository;

import com.programing.MatazorBank.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    boolean existsByAccountNumber(String AccountNumber);
    User findByAccountNumber(String accountNumber);
}
