import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SongResponse } from './song.service';

export interface AlbumRequest {
  title: string;
  description?: string;
  coverImageUrl?: string;
  releaseDate?: string;
}

export interface AlbumResponse {
  id: number;
  title: string;
  description?: string;
  coverImageUrl?: string;
  releaseDate?: string;
  songCount: number;
  songs: SongResponse[];
  createdAt: string;
}

@Injectable({
  providedIn: 'root',
})
export class AlbumService {
  private readonly baseUrl = `${environment.apiUrl}/artists/albums`;

  constructor(private readonly http: HttpClient) {}

  createAlbum(albumData: AlbumRequest): Observable<AlbumResponse> {
    return this.http.post<AlbumResponse>(this.baseUrl, albumData);
  }

  getAlbums(): Observable<AlbumResponse[]> {
    return this.http.get<AlbumResponse[]>(this.baseUrl);
  }

  getAlbumById(albumId: number): Observable<AlbumResponse> {
    return this.http.get<AlbumResponse>(`${this.baseUrl}/${albumId}`);
  }

  updateAlbum(albumId: number, albumData: Partial<AlbumRequest>): Observable<AlbumResponse> {
    return this.http.put<AlbumResponse>(`${this.baseUrl}/${albumId}`, albumData);
  }

  deleteAlbum(albumId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${albumId}`);
  }
}
