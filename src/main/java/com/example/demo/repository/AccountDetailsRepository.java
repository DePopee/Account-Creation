package com.example.demo.repository;

import com.example.demo.entity.AccountDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository
        extends JpaRepository<AccountDetailsEntity, Long> {
    boolean existsByAccountNumber(String accountNumber);

}