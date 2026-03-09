import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, RegisterRequest, UserRole } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  registerForm: FormGroup;
  loading = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private readonly router: Router,
  ) {
    this.registerForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
      lastName: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(30)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(64)]],
      role: ['USER', Validators.required],
    });
  }

  onSubmit(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    this.loading = true;

    const formValue = this.registerForm.value as {
      firstName: string;
      lastName: string;
      email: string;
      password: string;
      role: UserRole;
    };

    const payload: RegisterRequest = {
      firstName: formValue.firstName.trim(),
      lastName: formValue.lastName.trim(),
      email: formValue.email.trim(),
      password: formValue.password,
      role: formValue.role,
    };

    this.authService.register(payload).subscribe({
      next: () => {
        this.successMessage = 'Registration successful';
        this.loading = false;
        this.router.navigate(['/home/login'], { queryParams: { registered: 'true' } });
      },
      error: (error) => {
        const backendMessage = error?.error?.message || error?.error?.error || error?.message || '';
        this.errorMessage = backendMessage || 'Registration failed. Please try again.';
        this.loading = false;
      },
    });
  }
}
