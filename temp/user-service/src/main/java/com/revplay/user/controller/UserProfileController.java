package com.revplay.user.controller;

import com.revplay.user.dto.UserProfileRequest;
import com.revplay.user.dto.UserProfileResponse;
import com.revplay.user.dto.UserStatsResponse;
import com.revplay.user.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(@RequestHeader("X-User-Id") Long userId) {
        UserProfileResponse profile = userProfileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/profile")
    public ResponseEntity<UserProfileResponse> createProfile(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody UserProfileRequest request) {
        UserProfileResponse profile = userProfileService.createProfile(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody UserProfileRequest request) {
        UserProfileResponse profile = userProfileService.updateProfile(userId, request);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/stats")
    public ResponseEntity<UserStatsResponse> getStats(@RequestHeader("X-User-Id") Long userId) {
        UserStatsResponse stats = userProfileService.getStats(userId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long userId) {
        UserProfileResponse profile = userProfileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }
}
