import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface ArtistProfileRequest {
  stageName: string;
  bio?: string;
  profileImageUrl?: string;
}

export interface ArtistProfileResponse {
  id: number;
  userId: number;
  stageName: string;
  bio?: string;
  profileImageUrl?: string;
  verified: boolean;
  songCount: number;
  albumCount: number;
  totalPlays: number;
  createdAt: string;
}

@Injectable({
  providedIn: 'root',
})
export class ArtistService {
  private readonly baseUrl = `${environment.apiUrl}/artists`;

  constructor(private readonly http: HttpClient) {}

  getMyProfile(): Observable<ArtistProfileResponse> {
    return this.http.get<ArtistProfileResponse>(`${this.baseUrl}/profile`);
  }

  createProfile(data: ArtistProfileRequest): Observable<ArtistProfileResponse> {
    return this.http.post<ArtistProfileResponse>(`${this.baseUrl}/profile`, data);
  }

  updateMyProfile(data: ArtistProfileRequest): Observable<ArtistProfileResponse> {
    return this.http.put<ArtistProfileResponse>(`${this.baseUrl}/profile`, data);
  }

  getArtistById(artistId: number): Observable<ArtistProfileResponse> {
    return this.http.get<ArtistProfileResponse>(`${this.baseUrl}/${artistId}`);
  }
}
