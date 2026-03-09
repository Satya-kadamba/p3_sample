import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface PageResponse<T> {
  content: T[];
  page: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
}

export interface SongCatalogResponse {
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
  playCount?: number;
  createdAt?: string;
}

export interface GenreResponse {
  name: string;
  songCount: number;
}

export interface SearchRequest {
  query: string;
  genre?: string;
  artistName?: string;
}

export interface SearchResponse {
  songs: SongCatalogResponse[];
  totalResults: number;
  page: number;
  pageSize: number;
}

export interface TrendingResponse {
  songs: SongCatalogResponse[];
  period: 'DAILY' | 'WEEKLY' | 'MONTHLY';
}

@Injectable({ providedIn: 'root' })
export class SongLibraryService {
  private readonly baseUrl = `${environment.apiUrl}/catalog`;

  constructor(private readonly http: HttpClient) {}

  getPublicSongs(page = 0, size = 20): Observable<PageResponse<SongCatalogResponse>> {
    return this.http.get<PageResponse<SongCatalogResponse>>(`${this.baseUrl}/songs`, {
      params: {
        page,
        size,
      },
    });
  }

  getSongById(songId: number): Observable<SongCatalogResponse> {
    return this.http.get<SongCatalogResponse>(`${this.baseUrl}/songs/${songId}`);
  }

  getGenres(): Observable<GenreResponse[]> {
    return this.http.get<GenreResponse[]>(`${this.baseUrl}/genres`);
  }

  getSongsByGenre(genre: string, page = 0, size = 20): Observable<PageResponse<SongCatalogResponse>> {
    return this.http.get<PageResponse<SongCatalogResponse>>(`${this.baseUrl}/genres/${encodeURIComponent(genre)}/songs`, {
      params: {
        page,
        size,
      },
    });
  }

  searchSongs(query: string, page = 0, size = 20): Observable<SearchResponse> {
    return this.http.get<SearchResponse>(`${this.baseUrl}/search`, {
      params: {
        q: query,
        page,
        size,
      },
    });
  }

  advancedSearch(searchRequest: SearchRequest, page = 0, size = 20): Observable<SearchResponse> {
    return this.http.post<SearchResponse>(`${this.baseUrl}/search/advanced`, searchRequest, {
      params: {
        page,
        size,
      },
    });
  }

  getTrending(limit = 20): Observable<TrendingResponse> {
    return this.http.get<TrendingResponse>(`${this.baseUrl}/trending`, {
      params: { limit },
    });
  }

  getTrendingByPeriod(period: 'daily' | 'weekly' | 'monthly', limit = 20): Observable<TrendingResponse> {
    return this.http.get<TrendingResponse>(`${this.baseUrl}/trending/${period}`, {
      params: { limit },
    });
  }
}
