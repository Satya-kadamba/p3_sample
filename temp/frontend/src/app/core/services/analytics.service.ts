import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface AnalyticsOverviewResponse {
  totalPlays: number;
  uniqueListeners: number;
  totalListeningTimeMinutes: number;
  averagePlayDuration: number;
  topSongId?: number;
  topSongTitle?: string;
  period: string;
}

export interface SongPerformanceResponse {
  songId: number;
  title: string;
  playCount: number;
  uniqueListeners: number;
  averageListenDuration: number;
  completionRate: number;
}

export interface TopSongsResponse {
  songs: SongPerformanceResponse[];
  period: string;
  artistId: number;
}

export interface DailyTrendData {
  date: string;
  playCount: number;
  uniqueListeners: number;
  totalMinutes: number;
}

export interface ListeningTrendResponse {
  trends: DailyTrendData[];
}

export interface TopListenerResponse {
  userId: number;
  username: string;
  playCount: number;
  totalListeningMinutes: number;
}

@Injectable({
  providedIn: 'root',
})
export class AnalyticsService {
  private readonly baseUrl = `${environment.apiUrl}/analytics`;

  constructor(private readonly http: HttpClient) {}

  getOverview(artistId: number, period = 'ALL_TIME'): Observable<AnalyticsOverviewResponse> {
    return this.http.get<AnalyticsOverviewResponse>(`${this.baseUrl}/artist/${artistId}/overview`, {
      params: { period },
    });
  }

  getSongPerformance(artistId: number): Observable<SongPerformanceResponse[]> {
    return this.http.get<SongPerformanceResponse[]>(`${this.baseUrl}/artist/${artistId}/songs`);
  }

  getTopSongs(artistId: number, limit = 10, period = 'ALL_TIME'): Observable<TopSongsResponse> {
    return this.http.get<TopSongsResponse>(`${this.baseUrl}/artist/${artistId}/top`, {
      params: { limit, period },
    });
  }

  getTrends(artistId: number, startDate?: string, endDate?: string): Observable<ListeningTrendResponse> {
    const params: Record<string, string> = {};
    if (startDate) {
      params['startDate'] = startDate;
    }
    if (endDate) {
      params['endDate'] = endDate;
    }

    return this.http.get<ListeningTrendResponse>(`${this.baseUrl}/artist/${artistId}/trends`, {
      params,
    });
  }

  getTopListeners(artistId: number, limit = 10): Observable<TopListenerResponse[]> {
    return this.http.get<TopListenerResponse[]>(`${this.baseUrl}/artist/${artistId}/listeners`, {
      params: { limit },
    });
  }

  getHealth(): Observable<string> {
    return this.http.get(`${this.baseUrl}/health`, { responseType: 'text' });
  }
}
