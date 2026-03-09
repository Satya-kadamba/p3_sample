package com.revplay.auth.service;

import com.revplay.auth.dto.AuthResponse;
import com.revplay.auth.dto.LoginRequest;
import com.revplay.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    boolean validateToken(String token);
}
