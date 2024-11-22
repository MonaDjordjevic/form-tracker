package com.example.form_tracker.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {

    private String username;
    private String password;
}