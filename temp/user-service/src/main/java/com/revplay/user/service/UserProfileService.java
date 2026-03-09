package com.revplay.user.service;

import com.revplay.user.dto.UserProfileRequest;
import com.revplay.user.dto.UserProfileResponse;
import com.revplay.user.dto.UserStatsResponse;
import com.revplay.user.entity.UserProfile;
import com.revplay.user.exception.ResourceNotFoundException;
import com.revplay.user.repository.FavoriteRepository;
import com.revplay.user.repository.PlaylistRepository;
import com.revplay.user.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final PlaylistRepository playlistRepository;
    private final FavoriteRepository favoriteRepository;

    public UserProfileService(UserProfileRepository userProfileRepository,
                            PlaylistRepository playlistRepository,
                            FavoriteRepository favoriteRepository) {
        this.userProfileRepository = userProfileRepository;
        this.playlistRepository = playlistRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for userId: " + userId));
        return mapToResponse(profile);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfileByUserId(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for userId: " + userId));
        return mapToResponse(profile);
    }

    @Transactional
    public UserProfileResponse createProfile(Long userId, UserProfileRequest request) {
        // Check if profile already exists
        if (userProfileRepository.findByUserId(userId).isPresent()) {
            throw new IllegalStateException("Profile already exists for userId: " + userId);
        }

        UserProfile profile = UserProfile.builder()
                .userId(userId)
                .username(request.getUsername())
                .bio(request.getBio())
                .profileImageUrl(request.getProfileImageUrl())
                .build();

        UserProfile savedProfile = userProfileRepository.save(profile);
        return mapToResponse(savedProfile);
    }

    @Transactional
    public UserProfileResponse updateProfile(Long userId, UserProfileRequest request) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for userId: " + userId));

        if (request.getUsername() != null) {
            profile.setUsername(request.getUsername());
        }
        if (request.getBio() != null) {
            profile.setBio(request.getBio());
        }
        if (request.getProfileImageUrl() != null) {
            profile.setProfileImageUrl(request.getProfileImageUrl());
        }

        UserProfile updatedProfile = userProfileRepository.save(profile);
        return mapToResponse(updatedProfile);
    }

    @Transactional(readOnly = true)
    public UserStatsResponse getStats(Long userId) {
        Long playlistCount = playlistRepository.countByUserId(userId);
        Long favoriteCount = favoriteRepository.countByUserId(userId);

        return UserStatsResponse.builder()
                .playlistCount(playlistCount)
                .favoriteCount(favoriteCount)
                .totalListeningTime(0L) // This would require tracking from streaming service
                .build();
    }

    private UserProfileResponse mapToResponse(UserProfile profile) {
        return UserProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .username(profile.getUsername())
                .bio(profile.getBio())
                .profileImageUrl(profile.getProfileImageUrl())
                .createdAt(profile.getCreatedAt())
                .build();
    }
}
