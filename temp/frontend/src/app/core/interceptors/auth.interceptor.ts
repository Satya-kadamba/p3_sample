import { Injectable } from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, filter, finalize, switchMap, take, tap } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  private refreshedToken$ = new BehaviorSubject<string | null>(null);

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
  ) {}

  intercept(
    req: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const token = this.authService.getToken();
    const isAuthEndpoint = req.url.includes('/api/auth/');

    if (isAuthEndpoint) {
      return next.handle(req);
    }

    const authReq = token ? this.addAuthHeader(req, token) : req;
    return next.handle(authReq).pipe(
      catchError((error: unknown) => {
        if (!(error instanceof HttpErrorResponse) || error.status !== 401) {
          return throwError(() => error);
        }

        return this.handleUnauthorized(req, next);
      })
    );
  }

  private handleUnauthorized(
    req: HttpRequest<unknown>,
    next: HttpHandler,
  ): Observable<HttpEvent<unknown>> {
    const refreshToken = this.authService.getRefreshToken();
    if (!refreshToken) {
      this.forceLogout();
      return throwError(() => new Error('Missing refresh token'));
    }

    if (this.isRefreshing) {
      return this.refreshedToken$.pipe(
        filter((token): token is string => token !== null),
        take(1),
        switchMap((token) => next.handle(this.addAuthHeader(req, token))),
      );
    }

    this.isRefreshing = true;
    this.refreshedToken$.next(null);

    return this.authService.refreshToken(refreshToken).pipe(
      tap((response) => {
        this.authService.saveAccessToken(response.accessToken);
        localStorage.setItem('refreshToken', response.refreshToken);
        this.refreshedToken$.next(response.accessToken);
      }),
      switchMap((response) =>
        next.handle(this.addAuthHeader(req, response.accessToken)),
      ),
      catchError((refreshError) => {
        this.forceLogout();
        return throwError(() => refreshError);
      }),
      finalize(() => {
        this.isRefreshing = false;
      }),
    );
  }

  private addAuthHeader(req: HttpRequest<unknown>, token: string): HttpRequest<unknown> {
    return req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  private forceLogout(): void {
    this.authService.logout();
    this.router.navigate(['/home/login']);
  }
}
