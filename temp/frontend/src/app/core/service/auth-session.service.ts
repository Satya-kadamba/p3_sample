import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthSessionService {
  // Temporary dev fallback. Remove once real auth is wired.
  private readonly fallbackUserId = 1;

  getCurrentUserId(): number | null {
    const directId =
      this.readIdFromStorageKey('userId') ??
      this.readIdFromStorageKey('auth_user_id') ??
      this.readIdFromStorageKey('current_user_id');

    if (directId !== null) {
      return directId;
    }

    const jsonId = this.readIdFromJsonStorage('currentUser') ?? this.readIdFromJsonStorage('user');
    if (jsonId !== null) {
      return jsonId;
    }

    const token =
      this.readString('accessToken') ??
      this.readString('token') ??
      this.readString('jwt') ??
      this.readTokenFromJsonStorage('auth') ??
      this.readTokenFromJsonStorage('currentUser');

    if (!token) {
      return this.fallbackUserId;
    }

    return this.readIdFromJwt(token) ?? this.fallbackUserId;
  }

  setCurrentUserId(userId: number): void {
    localStorage.setItem('userId', String(userId));
  }

  clearCurrentUserId(): void {
    localStorage.removeItem('userId');
    sessionStorage.removeItem('userId');
  }

  private readIdFromStorageKey(key: string): number | null {
    const raw = this.readString(key);
    if (!raw) {
      return null;
    }
    return this.toNumber(raw);
  }

  private readIdFromJsonStorage(key: string): number | null {
    const raw = this.readString(key);
    if (!raw) {
      return null;
    }

    try {
      const parsed = JSON.parse(raw) as Record<string, unknown>;
      return (
        this.toNumber(parsed['id']) ??
        this.toNumber(parsed['userId']) ??
        this.toNumber(parsed['uid']) ??
        null
      );
    } catch {
      return null;
    }
  }

  private readTokenFromJsonStorage(key: string): string | null {
    const raw = this.readString(key);
    if (!raw) {
      return null;
    }

    try {
      const parsed = JSON.parse(raw) as Record<string, unknown>;
      const token = parsed['token'] ?? parsed['accessToken'] ?? parsed['jwt'];
      return typeof token === 'string' ? token : null;
    } catch {
      return null;
    }
  }

  private readIdFromJwt(token: string): number | null {
    const parts = token.split('.');
    if (parts.length < 2) {
      return null;
    }

    try {
      const payloadBase64 = parts[1].replace(/-/g, '+').replace(/_/g, '/');
      const padded = payloadBase64 + '='.repeat((4 - (payloadBase64.length % 4)) % 4);
      const payload = JSON.parse(atob(padded)) as Record<string, unknown>;

      return (
        this.toNumber(payload['userId']) ??
        this.toNumber(payload['id']) ??
        this.toNumber(payload['uid']) ??
        this.toNumber(payload['sub']) ??
        null
      );
    } catch {
      return null;
    }
  }

  private readString(key: string): string | null {
    const fromLocal = localStorage.getItem(key);
    if (fromLocal !== null) {
      return fromLocal;
    }
    return sessionStorage.getItem(key);
  }

  private toNumber(value: unknown): number | null {
    if (typeof value === 'number' && Number.isFinite(value)) {
      return value;
    }
    if (typeof value === 'string' && value.trim() !== '') {
      const parsed = Number(value);
      return Number.isFinite(parsed) ? parsed : null;
    }
    return null;
  }
}
