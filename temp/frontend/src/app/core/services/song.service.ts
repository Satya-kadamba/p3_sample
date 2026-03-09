import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export type SongVisibility = 'PUBLIC' | 'PRIVATE' | 'UNLISTED';

export interface SongRequest {
  title: string;
  duration: number;
  genre?: string;
  fileUrl: string;
  coverImageUrl?: string;
  visibility: SongVisibility;
  albumId?: number;
}

export interface SongResponse {
  id: number;
  title: string;
  artistId: number;
  artistName: string;
  albumId?: number;
  albumTitle?: string;
  duration: number;
  genre?: string;
  fileUrl: string;
  coverImageUrl?: string;
  visibility: SongVisibility;
  playCount: number;
  createdAt: string;
}

@Injectable({
  providedIn: 'root',
})
export class SongService {
  private readonly baseUrl = `${environment.apiUrl}/artists/songs`;

  constructor(private readonly http: HttpClient) {}

  uploadSong(data: SongRequest): Observable<SongResponse> {
    return this.http.post<SongResponse>(this.baseUrl, data);
  }

  getSongs(): Observable<SongResponse[]> {
    return this.http.get<SongResponse[]>(this.baseUrl);
  }

  getSong(songId: number): Observable<SongResponse> {
    return this.http.get<SongResponse>(`${this.baseUrl}/${songId}`);
  }

  updateSong(songId: number, data: Partial<SongRequest>): Observable<SongResponse> {
    return this.http.put<SongResponse>(`${this.baseUrl}/${songId}`, data);
  }

  deleteSong(songId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${songId}`);
  }

  updateVisibility(songId: number, visibility: SongVisibility): Observable<SongResponse> {
    return this.http.put<SongResponse>(`${this.baseUrl}/${songId}/visibility`, {}, {
      params: { visibility },
    });
  }
}
