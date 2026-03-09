import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { ArtistProfileRequest, ArtistProfileResponse, ArtistService } from 'src/app/core/services/artist.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  profile: ArtistProfileResponse | null = null;
  form: ArtistProfileRequest = {
    stageName: '',
    bio: '',
    profileImageUrl: '',
  };
  isEditMode = false;
  creating = false;
  errorMessage = '';

  constructor(
    private readonly artistService: ArtistService,
    private readonly router: Router,
    private readonly authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile(): void {
    this.errorMessage = '';
    this.artistService.getMyProfile().subscribe({
      next: (res) => {
        this.profile = res;
        this.form = {
          stageName: res.stageName,
          bio: res.bio ?? '',
          profileImageUrl: res.profileImageUrl ?? '',
        };
        this.creating = false;
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 404) {
          this.creating = true;
          this.profile = null;
          return;
        }
        this.errorMessage = err?.error?.message || 'Failed to load profile';
      },
    });
  }

  saveProfile(): void {
    if (!this.form.stageName.trim()) {
      this.errorMessage = 'Stage name is required';
      return;
    }

    const payload: ArtistProfileRequest = {
      stageName: this.form.stageName.trim(),
      bio: this.form.bio?.trim() ?? '',
      profileImageUrl: this.form.profileImageUrl?.trim() ?? '',
    };

    const request$ = this.creating
      ? this.artistService.createProfile(payload)
      : this.artistService.updateMyProfile(payload);

    request$.subscribe({
      next: (res) => {
        this.profile = res;
        localStorage.setItem('artistId', String(res.id));
        this.creating = false;
        this.isEditMode = false;
      },
      error: (err) => {
        this.errorMessage = err?.error?.message || 'Failed to save profile';
      },
    });
  }

  enableEdit(): void {
    this.isEditMode = true;
  }

  cancelEdit(): void {
    this.isEditMode = false;
    if (this.profile) {
      this.form = {
        stageName: this.profile.stageName,
        bio: this.profile.bio ?? '',
        profileImageUrl: this.profile.profileImageUrl ?? '',
      };
    }
  }

  logout(): void {
    this.authService.logout(false);
    this.router.navigate(['/home/login']);
  }
}
