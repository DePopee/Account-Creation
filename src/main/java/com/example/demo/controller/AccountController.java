package com.example.demo.controller;

import com.example.demo.dto.Request.CreateAccountRequest;
import com.example.demo.entity.AccountDetailsEntity;
import com.example.demo.entity.CustomerDetailsEntity;
import com.example.demo.repository.AccountDetailsRepository;
import com.example.demo.repository.CustomerDetailsRepository;
import com.example.demo.service.AccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountDetails accountDetails;
    private final AccountDetailsRepository accountDetailsRepository;
    private final CustomerDetailsRepository customerDetailsRepository;

    @PostMapping("/create")
    public ResponseEntity<AccountDetailsEntity> createAccount(@RequestBody CreateAccountRequest request) {
        CustomerDetailsEntity newCustomer = new CustomerDetailsEntity();
        newCustomer.setFullName(request.getFullName());
        newCustomer.setPhoneNumber(request.getPhoneNumber());
        newCustomer.setAge(request.getAge());
        newCustomer.setResidentialAddress(request.getResidentialAddress());
        newCustomer.setBVN(request.getBVN());
        newCustomer.setUsername(request.getUsername());
        newCustomer.setPassword(request.getPassword());

        CustomerDetailsEntity savedCustomer = customerDetailsRepository.save(newCustomer);

        AccountDetailsEntity newAccount = accountDetails.createAccount(savedCustomer);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    // Fetch all accounts
    @GetMapping("/all")
    public ResponseEntity<List<AccountDetailsEntity>> getAllAccounts() {
        List<AccountDetailsEntity> accounts = accountDetailsRepository.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // Fetch a specific account by ID
    @GetMapping("/find/{id}")
    public ResponseEntity<AccountDetailsEntity> getAccountById(@PathVariable Long id) {
        Optional<AccountDetailsEntity> accountOpt = accountDetailsRepository.findById(id);
        return accountOpt.map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an account
    @PutMapping("/update/{id}")
    public ResponseEntity<AccountDetailsEntity> updateAccount(@PathVariable Long id, @RequestBody AccountDetailsEntity accountDetails) {
        Optional<AccountDetailsEntity> accountOpt = accountDetailsRepository.findById(id);
        if (accountOpt.isPresent()) {
            AccountDetailsEntity existingAccount = accountOpt.get();
            existingAccount.setCustomer(accountDetails.getCustomer());
            AccountDetailsEntity updatedAccount = accountDetailsRepository.save(existingAccount);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        if (accountDetailsRepository.existsById(id)) {
            accountDetailsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
