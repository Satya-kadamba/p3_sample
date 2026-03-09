import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface Song {
  id: number;
  title: string;
  url: string;
}

interface CatalogSong {
  id: number;
  title: string;
  fileUrl: string;
}

interface CatalogPageResponse {
  content: CatalogSong[];
}

@Injectable({
  providedIn: 'root',
})
export class SongService {
  private readonly baseUrl = `${environment.apiUrl}/catalog`;

  constructor(private readonly http: HttpClient) {}

  getAllSongs(): Observable<Song[]> {
    return this.http
      .get<CatalogPageResponse>(`${this.baseUrl}/songs`, {
        params: {
          page: 0,
          size: 200,
        },
      })
      .pipe(
        map((response) =>
          (response?.content ?? []).map((song) => ({
            id: song.id,
            title: song.title,
            url: song.fileUrl,
          }))
        )
      );
  }
}
