import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SongService {
  private baseUrl = `${environment.apiUrl}/music`;

  constructor(private http: HttpClient) {}

  getSongs(
    page: number,
    size: number,
    filters?: { title?: string; genre?: string; album?: string; releaseYear?: string; sort?: string }
  ): Observable<any> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);   

    if (filters?.title) params = params.set('title', filters.title);
    if (filters?.genre) params = params.set('genre', filters.genre);
    if (filters?.album) params = params.set('album', filters.album);
    if (filters?.releaseYear) params = params.set('releaseYear', filters.releaseYear);
    if (filters?.sort) params = params.set('sort', filters.sort);

    return this.http.get(`${this.baseUrl}/songs`, { params });
  }

  search(keyword: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/search?keyword=${encodeURIComponent(keyword)}`);
  }

  getSongById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/songs/${id}`);
  }

  getArtistById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/artists/${id}`);
  }

  getAlbumById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/albums/${id}`);
  }
}
