import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ArtistService } from 'src/app/core/services/artist.service';

@Component({
  selector: 'app-artist-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  registerForm: FormGroup;
  errorMessage = '';

  constructor(
    private readonly fb: FormBuilder,
    private readonly artistService: ArtistService,
    private readonly router: Router
  ) {
    this.registerForm = this.fb.group({
      stageName: ['', Validators.required],
      bio: [''],
      profileImageUrl: [''],
    });
  }

  register(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    this.errorMessage = '';
    this.artistService.createProfile(this.registerForm.value).subscribe({
      next: (res) => {
        localStorage.setItem('artistId', String(res.id));
        this.router.navigate(['/artist/profile']);
      },
      error: (err) => {
        this.errorMessage = err?.error?.message || 'Failed to create artist profile';
      },
    });
  }
}
