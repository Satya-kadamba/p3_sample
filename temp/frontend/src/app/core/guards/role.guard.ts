import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const expectedRole = route.data?.['role'] as string | undefined;

  if (!authService.isLoggedIn()) {
    router.navigate(['/home/login']);
    return false;
  }

  if (!expectedRole || authService.hasRole(expectedRole)) {
    return true;
  }

  router.navigate([authService.getDefaultRouteForCurrentRole()]);
  return false;
};
