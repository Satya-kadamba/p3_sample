import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import {
  UserProfileRequest,
  UserProfileResponse,
  UserService,
  UserStatsResponse,
} from 'src/app/core/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  profile: UserProfileResponse | null = null;
  form: UserProfileRequest = {
    username: '',
    bio: '',
    profileImageUrl: '',
  };

  stats: UserStatsResponse = {
    playlistCount: 0,
    favoriteCount: 0,
    totalListeningTime: 0,
  };

  loading = false;
  creating = false;
  errorMessage = '';

  constructor(
    private readonly userService: UserService,
    private readonly authService: AuthService,
    private readonly router: Router,
  ) {}

  ngOnInit(): void {
    this.loadProfile();
    this.loadStats();
  }

  loadProfile(): void {
    this.errorMessage = '';
    this.userService.getProfile().subscribe({
      next: (res) => {
        this.profile = res;
        this.form = {
          username: res.username,
          bio: res.bio ?? '',
          profileImageUrl: res.profileImageUrl ?? '',
        };
        this.creating = false;
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 404) {
          this.creating = true;
          return;
        }
        this.errorMessage = 'Failed to load profile';
      },
    });
  }

  loadStats(): void {
    this.userService.getStats().subscribe({
      next: (res) => {
        this.stats = res;
      },
      error: () => {
        this.stats = {
          playlistCount: 0,
          favoriteCount: 0,
          totalListeningTime: 0,
        };
      },
    });
  }

  save(): void {
    if (!this.form.username.trim() || this.loading) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const payload: UserProfileRequest = {
      username: this.form.username.trim(),
      bio: this.form.bio?.trim() ?? '',
      profileImageUrl: this.form.profileImageUrl?.trim() ?? '',
    };

    const request$ = this.creating
      ? this.userService.createProfile(payload)
      : this.userService.updateProfile(payload);

    request$.subscribe({
      next: (res) => {
        this.profile = res;
        this.creating = false;
        this.loading = false;
        this.loadStats();
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Failed to save profile';
      },
    });
  }

  validateToken(): void {
    const token = this.authService.getToken();
    if (!token) {
      this.errorMessage = 'Missing access token';
      return;
    }

    this.authService.validateToken(token).subscribe({
      next: (res) => {
        this.errorMessage = res.valid ? 'Token is valid' : 'Token is invalid';
      },
      error: () => {
        this.errorMessage = 'Token validation failed';
      },
    });
  }

  refreshToken(): void {
    const refreshToken = this.authService.getRefreshToken();
    if (!refreshToken) {
      this.errorMessage = 'Missing refresh token';
      return;
    }

    this.authService.refreshToken(refreshToken).subscribe({
      next: (res) => {
        this.authService.saveAccessToken(res.accessToken);
        localStorage.setItem('refreshToken', res.refreshToken);
        this.errorMessage = 'Token refreshed';
      },
      error: () => {
        this.errorMessage = 'Token refresh failed';
      },
    });
  }

  logout(): void {
    this.authService.logout(false);
    this.router.navigate(['/home/login']);
  }
}
