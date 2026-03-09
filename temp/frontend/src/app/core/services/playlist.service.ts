import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface PlaylistSongRequest {
  songId: number;
  position?: number;
}

export interface PlaylistRequest {
  name: string;
  description?: string;
  isPublic?: boolean;
}

export interface PlaylistSong {
  id: number;
  title: string;
  artistName: string;
  albumTitle?: string;
}

export interface PlaylistResponse {
  id: number;
  name: string;
  description?: string;
  isPublic: boolean;
  songCount: number;
  songs: PlaylistSong[];
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class PlaylistService {
  private readonly baseUrl = `${environment.apiUrl}/playlists`;

  constructor(private readonly http: HttpClient) {}

  create(data: PlaylistRequest): Observable<PlaylistResponse> {
    return this.http.post<PlaylistResponse>(this.baseUrl, data);
  }

  getAll(): Observable<PlaylistResponse[]> {
    return this.http.get<PlaylistResponse[]>(this.baseUrl);
  }

  getById(playlistId: number): Observable<PlaylistResponse> {
    return this.http.get<PlaylistResponse>(`${this.baseUrl}/${playlistId}`);
  }

  update(playlistId: number, data: PlaylistRequest): Observable<PlaylistResponse> {
    return this.http.put<PlaylistResponse>(`${this.baseUrl}/${playlistId}`, data);
  }

  delete(playlistId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${playlistId}`);
  }

  addSong(playlistId: number, request: PlaylistSongRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${playlistId}/songs`, request);
  }

  removeSong(playlistId: number, songId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${playlistId}/songs/${songId}`);
  }
}
