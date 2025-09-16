package com.wbf.mutuelle.controllers;

import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;



@Setter
@RequestMapping("/auth")
public class AuthRequest {


    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String password;
}
