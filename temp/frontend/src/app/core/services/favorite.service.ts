import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface FavoriteResponse {
  id: number;
  songId: number;
  songTitle: string;
  artistName: string;
  addedAt: string;
}

@Injectable({ providedIn: 'root' })
export class FavoriteService {
  private readonly baseUrl = `${environment.apiUrl}/favorites`;

  constructor(private readonly http: HttpClient) {}

  add(songId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${songId}`, {});
  }

  remove(songId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${songId}`);
  }

  get(): Observable<FavoriteResponse[]> {
    return this.http.get<FavoriteResponse[]>(this.baseUrl);
  }

  check(songId: number): Observable<{ isFavorite: boolean }> {
    return this.http.get<{ isFavorite: boolean }>(`${this.baseUrl}/${songId}/check`);
  }
}
