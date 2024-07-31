package com.example.demo.dto.Request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateAccountRequest {

    private String fullName;
    private String phoneNumber;
    private int age;
    private String residentialAddress;
    @NonNull
    private String BVN;
    private String username;
    private String password;
}
