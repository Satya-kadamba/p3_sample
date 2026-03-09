import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export type UserRole = 'USER' | 'ARTIST';

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  role: UserRole;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  userId: number;
  email: string;
  role: UserRole;
}

export interface TokenRefreshResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl = `${environment.apiUrl}/auth`;

  constructor(private readonly http: HttpClient) {}

  register(data: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/register`, data);
  }

  login(data: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, data);
  }

  refreshToken(refreshToken: string): Observable<TokenRefreshResponse> {
    return this.http.post<TokenRefreshResponse>(`${this.baseUrl}/refresh`, { refreshToken });
  }

  validateToken(token: string): Observable<{ valid: boolean; userId?: number; email?: string }> {
    return this.http.get<{ valid: boolean; userId?: number; email?: string }>(
      `${this.baseUrl}/validate`,
      { params: { token } }
    );
  }

  logoutApi(refreshToken: string): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.baseUrl}/logout`, { refreshToken });
  }

  saveSession(auth: AuthResponse): void {
    localStorage.setItem('token', auth.accessToken);
    localStorage.setItem('refreshToken', auth.refreshToken);
    localStorage.setItem('userEmail', auth.email);
    localStorage.setItem('userId', String(auth.userId));
    localStorage.setItem('userRole', auth.role);

    if (auth.role === 'ARTIST') {
      localStorage.setItem('artistId', String(auth.userId));
    } else {
      localStorage.removeItem('artistId');
    }
  }

  saveAccessToken(accessToken: string): void {
    localStorage.setItem('token', accessToken);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRefreshToken(): string | null {
    return localStorage.getItem('refreshToken');
  }

  getCurrentUserId(): number | null {
    const fromPayload = this.getJwtPayload()?.['userId'];
    if (typeof fromPayload === 'number' && Number.isFinite(fromPayload)) {
      return fromPayload;
    }

    const fromStorage = localStorage.getItem('userId');
    if (!fromStorage) {
      return null;
    }
    const parsed = Number(fromStorage);
    return Number.isFinite(parsed) ? parsed : null;
  }

  getUserRole(): string | null {
    const payload = this.getJwtPayload();
    const fromToken = payload?.['role'];
    if (typeof fromToken === 'string' && fromToken.trim().length > 0) {
      return fromToken;
    }
    return localStorage.getItem('userRole');
  }

  hasRole(expectedRole: string): boolean {
    return this.getUserRole() === expectedRole;
  }

  getDefaultRouteForCurrentRole(): string {
    const role = this.getUserRole();
    return role === 'ARTIST' ? '/artist/dashboard' : '/browse';
  }

  logout(clearOnly = true): void {
    if (!clearOnly) {
      const refreshToken = this.getRefreshToken();
      if (refreshToken) {
        this.logoutApi(refreshToken).subscribe({
          next: () => this.clearSession(),
          error: () => this.clearSession(),
        });
        return;
      }
    }

    this.clearSession();
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    const refreshToken = this.getRefreshToken();

    if (!token) {
      return !!refreshToken;
    }

    const payload = this.getJwtPayload();
    if (!payload) {
      return !!refreshToken;
    }

    if (typeof payload['exp'] === 'number') {
      const nowInSeconds = Math.floor(Date.now() / 1000);
      if (payload['exp'] <= nowInSeconds) {
        return !!refreshToken;
      }
    }

    return true;
  }

  private clearSession(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('userId');
    localStorage.removeItem('userRole');
    localStorage.removeItem('artistId');
  }

  private getJwtPayload(): Record<string, unknown> | null {
    const token = this.getToken();
    if (!token) {
      return null;
    }

    try {
      const payloadPart = token.split('.')[1];
      if (!payloadPart) {
        return null;
      }

      const normalized = payloadPart.replace(/-/g, '+').replace(/_/g, '/');
      const padded = normalized + '='.repeat((4 - (normalized.length % 4)) % 4);
      return JSON.parse(atob(padded)) as Record<string, unknown>;
    } catch {
      return null;
    }
  }
}
