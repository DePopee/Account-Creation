package com.example.demo.service;

import com.example.demo.entity.AccountDetailsEntity;
import com.example.demo.entity.CustomerDetailsEntity;

public interface AccountDetails {
    AccountDetailsEntity createAccount(CustomerDetailsEntity customer);
    void sendAccountCreationEmail(CustomerDetailsEntity customer, AccountDetailsEntity account);
}