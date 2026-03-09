package com.revplay.auth.controller;

import com.revplay.auth.dto.*;
import com.revplay.auth.entity.RefreshToken;
import com.revplay.auth.entity.User;
import com.revplay.auth.exception.AuthException;
import com.revplay.auth.repository.UserRepository;
import com.revplay.auth.service.AuthService;
import com.revplay.auth.service.JwtService;
import com.revplay.auth.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
// @CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          RefreshTokenService refreshTokenService,
                          JwtService jwtService,
                          UserRepository userRepository) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .orElseThrow(() -> new AuthException("Refresh token not found"));

        refreshToken = refreshTokenService.verifyExpiration(refreshToken);

        User user = userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new AuthException("User not found"));

        String newAccessToken = jwtService.generateToken(user);

        TokenRefreshResponse response = TokenRefreshResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(requestRefreshToken)
                .tokenType("Bearer")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .orElseThrow(() -> new AuthException("Refresh token not found"));

        refreshTokenService.deleteByUserId(refreshToken.getUserId());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestParam String token) {
        boolean isValid = authService.validateToken(token);

        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);

        if (isValid) {
            Long userId = jwtService.extractUserId(token);
            String email = jwtService.extractEmail(token);
            response.put("userId", userId);
            response.put("email", email);
        }

        return ResponseEntity.ok(response);
    }
}
