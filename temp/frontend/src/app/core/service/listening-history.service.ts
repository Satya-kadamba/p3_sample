import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface ListeningHistoryItem {
  id: number;
  songId: number;
  songTitle: string;
  artistName: string;
  albumTitle: string;
  coverImageUrl?: string;
  playedAt: string;
  listenedDuration: number;
}

export interface UserHistoryStatsResponse {
  totalSongsPlayed: number;
  totalListeningTimeSeconds: number;
  uniqueSongsPlayed: number;
  topGenres: string[];
}

export interface PlayResponse {
  songId: number;
  title: string;
  artistName: string;
  albumTitle: string;
  fileUrl: string;
  coverImageUrl?: string;
  duration: number;
}

export interface NowPlayingResponse {
  songId: number;
  title: string;
  artistName: string;
  albumTitle: string;
  coverImageUrl?: string;
  startedAt: string;
}

interface HistoryPage {
  content: ListeningHistoryItem[];
}

@Injectable({
  providedIn: 'root',
})
export class ListeningHistoryService {
  private readonly baseUrl = environment.apiUrl;

  constructor(private readonly http: HttpClient) {}

  recordPlay(songId: number): Observable<PlayResponse> {
    return this.http.post<PlayResponse>(`${this.baseUrl}/player/play`, { songId });
  }

  updatePlayDuration(historyId: number, duration: number): Observable<{ message: string }> {
    return this.http.put<{ message: string }>(`${this.baseUrl}/player/play/${historyId}/duration`, {
      duration,
    });
  }

  getNowPlaying(): Observable<NowPlayingResponse> {
    return this.http.get<NowPlayingResponse>(`${this.baseUrl}/player/now-playing`);
  }

  getRecentHistory(page = 0, size = 20): Observable<ListeningHistoryItem[]> {
    return this.http
      .get<HistoryPage>(`${this.baseUrl}/history/recent`, {
        params: { page, size },
      })
      .pipe(map((response) => response.content ?? []));
  }

  clearHistory(): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.baseUrl}/history`);
  }

  getStats(): Observable<UserHistoryStatsResponse> {
    return this.http.get<UserHistoryStatsResponse>(`${this.baseUrl}/history/stats`);
  }

  getTopSongs(limit = 10): Observable<Array<Record<string, unknown>>> {
    return this.http.get<Array<Record<string, unknown>>>(`${this.baseUrl}/history/top-songs`, {
      params: { limit },
    });
  }
}
