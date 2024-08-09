package com.example.demo.service;

import com.example.demo.dto.Request.CreateAccountRequest;
import com.example.demo.entity.AccountDetailsEntity;
import com.example.demo.entity.CustomerDetailsEntity;
import com.example.demo.repository.AccountDetailsRepository;
import com.example.demo.repository.CustomerDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountDetailsService {

    private static final String ACCOUNT_PREFIX = "223";
    private static final int ACCOUNT_NUMBER_LENGTH = 10;
    private static final String EMAIL_TO = "toppyihans@gmail.com";
    private static final int MAX_ATTEMPTS = 100;


    private final AccountDetailsRepository accountDetailsRepository;
    private final CustomerDetailsRepository customerDetailsRepository;
    private final JavaMailSender emailSender;

    public List<AccountDetailsEntity> getAllAccounts() {
        return accountDetailsRepository.findAll();
    }


    public AccountDetailsEntity createAccount(CreateAccountRequest customer) {
        System.out.println("Customer: " + customer);

        CustomerDetailsEntity customerDetailsEntity = saveCustomer(customer);

        AccountDetailsEntity account = new AccountDetailsEntity();
        account.setCustomerId(customerDetailsEntity.getCustomerId());
        account.setAccountNumber(generateUniqueAccountNumber());

        AccountDetailsEntity savedAccount = accountDetailsRepository.save(account);

        sendAccountCreationEmail(customerDetailsEntity, savedAccount);

        return savedAccount;
    }

    public void sendAccountCreationEmail(CustomerDetailsEntity customer, AccountDetailsEntity account) {
        System.out.println("Customer:" + customer);
        System.out.println("Account;" + account);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(EMAIL_TO);
        message.setSubject("Account Created Successfully");
        message.setText("Dear " + customer.getFullName() + ",\n\n" +
                "Your account has been successfully created.\n" +
                "Account Number: " + account.getAccountNumber() + "\n\n" +
                "Thank you for choosing our services!");

        emailSender.send(message);
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        int attempts = 0;
        do {
            accountNumber = generateAccountNumber();
            attempts++;
            if (attempts >= MAX_ATTEMPTS) {
                throw new RuntimeException("Unable to generate a unique account number after " + MAX_ATTEMPTS + " attempts");
            }
        } while (accountDetailsRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    private String generateUniqueCustomerId() {
        String customerId;
        int attempts = 0;
        do {
            customerId = generateCustomerId();
            attempts++;
            if (attempts >= MAX_ATTEMPTS) {
                throw new RuntimeException("Unable to generate a unique customer ID after " + MAX_ATTEMPTS + " attempts");
            }
        } while (customerDetailsRepository.existsByCustomerId(customerId));
        return customerId;
    }

    private String generateCustomerId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(ACCOUNT_PREFIX);
        int remainingDigits = ACCOUNT_NUMBER_LENGTH - ACCOUNT_PREFIX.length();
        for (int i = 0; i < remainingDigits; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public CustomerDetailsEntity saveCustomer(CreateAccountRequest createAccountRequest) {


        CustomerDetailsEntity customerDetailsEntity = new CustomerDetailsEntity();
        customerDetailsEntity.setFullName(createAccountRequest.getFullName());
        customerDetailsEntity.setPhoneNumber(createAccountRequest.getPhoneNumber());
        customerDetailsEntity.setAge(createAccountRequest.getAge());
        customerDetailsEntity.setBvn(createAccountRequest.getBvn());
        customerDetailsEntity.setEmail(createAccountRequest.getEmail());
        customerDetailsEntity.setResidentialAddress(createAccountRequest.getResidentialAddress());
        customerDetailsEntity.setUsername(createAccountRequest.getUsername());
        customerDetailsEntity.setPassword(createAccountRequest.getPassword());
        customerDetailsEntity.setDateCreated(LocalDate.now());
        customerDetailsEntity.setTimeCreated(LocalTime.now());
        customerDetailsEntity.setCustomerId(generateUniqueCustomerId());
        customerDetailsRepository.save(customerDetailsEntity);

        return customerDetailsEntity;
    }
}