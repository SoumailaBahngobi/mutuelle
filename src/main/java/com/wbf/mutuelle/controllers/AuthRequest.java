package com.wbf.mutuelle.controllers;

import lombok.Data;

@Data
public class AuthRequest {
    private String username; // correspond à l'email
    private String password;
}
