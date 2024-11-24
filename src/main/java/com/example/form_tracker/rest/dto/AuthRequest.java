package com.example.form_tracker.rest.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;


@Data
@Builder
public class AuthRequest {

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;
}