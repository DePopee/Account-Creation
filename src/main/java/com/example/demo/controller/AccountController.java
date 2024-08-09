package com.example.demo.controller;

import com.example.demo.dto.Request.CreateAccountRequest;
import com.example.demo.entity.AccountDetailsEntity;
import com.example.demo.entity.CustomerDetailsEntity;
import com.example.demo.service.AccountDetailsService;
import com.example.demo.service.CustomerDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

 private final AccountDetailsService accountDetailsService;
 private final CustomerDetailsService customerDetailsService;

 @PostMapping("register/account")
 public ResponseEntity<Void> registerNewAccounts(@RequestBody CreateAccountRequest request) {
  System.out.println("\nREGISTER ACCOUNT ENDPOINT");
  accountDetailsService.createAccount(request);

  return new ResponseEntity<>(HttpStatus.CREATED);
 }

 @GetMapping("fetch/account")
 public List<AccountDetailsEntity> getAllAccounts() {
  System.out.println("\nFETCH ALL ACCOUNTS");
  return accountDetailsService.getAllAccounts();
 }

 @GetMapping("fetch/customer-details")
 public List<CustomerDetailsEntity> getAllCustomersDetails() {
  System.out.println("\nFETCH ALL CUSTOMERS DETAILS");
  return customerDetailsService.getAllCustomers(); // This method should be present in your CustomerDetailsService
 }

 @PutMapping("/{customerId}")
 public List<CustomerDetailsEntity>


}
