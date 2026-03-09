import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthResponse, AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;
  loading = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });

    if (this.route.snapshot.queryParamMap.get('registered') === 'true') {
      this.successMessage = 'Registration successful';
    }
  }

  onSubmit(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loading = true;

    const payload = this.loginForm.value as { email: string; password: string };
    this.authService.login(payload).subscribe({
      next: (response: AuthResponse) => {
        this.authService.saveSession(response);
        this.successMessage = 'Login successful';
        this.loading = false;
        this.router.navigate([this.authService.getDefaultRouteForCurrentRole()]);
      },
      error: (error) => {
        this.errorMessage = error?.error?.message || 'Login failed. Please try again.';
        this.loading = false;
      },
    });
  }
}
