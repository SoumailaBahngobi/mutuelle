package com.wbf.mutuelle.controllers;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String firstName;
    private String email;
    private String password;
    private String phone;
    private String role;
}
