import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface UserProfileRequest {
  username: string;
  bio?: string;
  profileImageUrl?: string;
}

export interface UserProfileResponse {
  id: number;
  userId: number;
  username: string;
  bio?: string;
  profileImageUrl?: string;
  createdAt: string;
}

export interface UserStatsResponse {
  playlistCount: number;
  favoriteCount: number;
  totalListeningTime: number;
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly baseUrl = `${environment.apiUrl}/users`;

  constructor(private readonly http: HttpClient) {}

  getProfile(): Observable<UserProfileResponse> {
    return this.http.get<UserProfileResponse>(`${this.baseUrl}/profile`);
  }

  createProfile(data: UserProfileRequest): Observable<UserProfileResponse> {
    return this.http.post<UserProfileResponse>(`${this.baseUrl}/profile`, data);
  }

  updateProfile(data: UserProfileRequest): Observable<UserProfileResponse> {
    return this.http.put<UserProfileResponse>(`${this.baseUrl}/profile`, data);
  }

  getStats(): Observable<UserStatsResponse> {
    return this.http.get<UserStatsResponse>(`${this.baseUrl}/stats`);
  }

  getUserById(userId: number): Observable<UserProfileResponse> {
    return this.http.get<UserProfileResponse>(`${this.baseUrl}/${userId}`);
  }
}
