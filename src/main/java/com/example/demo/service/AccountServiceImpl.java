package com.example.demo.service;

import com.example.demo.entity.AccountDetailsEntity;
import com.example.demo.entity.CustomerDetailsEntity;
import com.example.demo.repository.AccountDetailsRepository;
import com.example.demo.repository.CustomerDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountDetails {

    private static final String ACCOUNT_PREFIX = "223";
    private static final int ACCOUNT_NUMBER_LENGTH = 10;

    private static final String EMAIL_TO = "samuel.ogoh@stanbicibtc.com";


    private final AccountDetailsRepository accountDetailsRepository;


    private final CustomerDetailsRepository customerDetailsRepository;

    private final JavaMailSender emailSender;

    @Override
    public AccountDetailsEntity createAccount(CustomerDetailsEntity customer) {
        AccountDetailsEntity account = new AccountDetailsEntity();
        account.setCustomer(customer);
        account.setAccountNumber(generateUniqueAccountNumber());

        AccountDetailsEntity savedAccount = accountDetailsRepository.save(account);

        sendAccountCreationEmail(customer, savedAccount);

        return savedAccount;
    }

    @Override
    public void sendAccountCreationEmail(CustomerDetailsEntity customer, AccountDetailsEntity account) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Account Created Successfully");
        message.setText("Dear " + customer.getFullName() + ",\n\n" +
                "Your account has been successfully created.\n" +
                "Account Number: " + account.getAccountNumber() + "\n\n" +
                "Thank you for choosing our services!");

        emailSender.send(message);
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = generateAccountNumber();
        } while (accountDetailsRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
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
}