import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FavoriteService } from 'src/app/core/services/favorite.service';
import {
  GenreResponse,
  SearchRequest,
  SongCatalogResponse,
  SongLibraryService,
  TrendingResponse,
} from 'src/app/core/services/song-library.service';

@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css'],
})
export class BrowseComponent implements OnInit {
  songs: Array<SongCatalogResponse & { isFavorite: boolean }> = [];
  genres: GenreResponse[] = [];
  trendingSongs: SongCatalogResponse[] = [];

  currentPage = 0;
  pageSize = 20;

  advancedSearch: SearchRequest = {
    query: '',
    genre: '',
    artistName: '',
  };

  releaseDateFilter = '';
  sortBy = '';

  loading = false;
  errorMessage = '';

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly songLibraryService: SongLibraryService,
    private readonly favoriteService: FavoriteService,
  ) {}

  ngOnInit(): void {
    this.loadGenres();
    this.loadTrending('weekly');

    this.route.queryParamMap.subscribe((params) => {
      this.advancedSearch.query = params.get('q') ?? '';
      this.advancedSearch.artistName = params.get('artist') ?? '';
      this.advancedSearch.genre = params.get('genre') ?? '';
      this.releaseDateFilter = params.get('releaseDate') ?? '';
      this.sortBy = params.get('sort') ?? '';

      this.loadSongsForCurrentQuery();
    });
  }

  loadSongsForCurrentQuery(): void {
    this.loading = true;
    this.errorMessage = '';

    const hasSearch =
      this.advancedSearch.query.trim().length > 0 ||
      (this.advancedSearch.artistName || '').trim().length > 0 ||
      (this.advancedSearch.genre || '').trim().length > 0;

    if (hasSearch) {
      this.songLibraryService
        .advancedSearch(
          {
            query: this.advancedSearch.query || '',
            genre: this.advancedSearch.genre || undefined,
            artistName: this.advancedSearch.artistName || undefined,
          },
          this.currentPage,
          this.pageSize
        )
        .subscribe({
          next: (res) => this.updateSongs(res.songs),
          error: () => this.handleLoadError(),
        });
      return;
    }

    this.songLibraryService.getPublicSongs(this.currentPage, this.pageSize).subscribe({
      next: (res) => this.updateSongs(res.content),
      error: () => this.handleLoadError(),
    });
  }

  loadGenres(): void {
    this.songLibraryService.getGenres().subscribe({
      next: (rows) => {
        this.genres = rows;
      },
      error: () => {
        this.genres = [];
      },
    });
  }

  loadTrending(period: 'daily' | 'weekly' | 'monthly'): void {
    this.songLibraryService.getTrendingByPeriod(period, 10).subscribe({
      next: (res: TrendingResponse) => {
        this.trendingSongs = res.songs;
      },
      error: () => {
        this.trendingSongs = [];
      },
    });
  }

  runAdvancedSearch(): void {
    this.router.navigate(['/browse'], {
      queryParams: {
        q: this.advancedSearch.query || undefined,
        artist: this.advancedSearch.artistName || undefined,
        genre: this.advancedSearch.genre || undefined,
      },
    });
  }

  toggleFavorite(songId: number, isFavorite: boolean): void {
    const update = this.songs.map((song) =>
      song.id === songId ? { ...song, isFavorite: !isFavorite } : song
    );
    this.songs = update;

    const request$ = isFavorite ? this.favoriteService.remove(songId) : this.favoriteService.add(songId);

    request$.subscribe({
      next: () => {},
      error: () => {
        this.songs = this.songs.map((song) =>
          song.id === songId ? { ...song, isFavorite } : song
        );
      },
    });
  }

  viewSongDetails(songId: number): void {
    this.router.navigate(['/browse', songId], {
      queryParamsHandling: 'preserve',
    });
  }

  playSong(songId: number): void {
    this.router.navigate(['/player'], {
      queryParams: { songId },
    });
  }

  private loadFavorites(): void {
    this.favoriteService.get().subscribe({
      next: (favorites) => {
        const ids = new Set(favorites.map((row) => row.songId));
        this.songs = this.songs.map((song) => ({
          ...song,
          isFavorite: ids.has(song.id),
        }));
      },
      error: () => {},
    });
  }

  private sortSongs(rows: SongCatalogResponse[]): SongCatalogResponse[] {
    if (!this.sortBy) {
      return rows;
    }

    return [...rows].sort((a, b) => {
      const artistA = (a.artistName ?? '').toLowerCase();
      const artistB = (b.artistName ?? '').toLowerCase();
      const albumA = (a.albumTitle ?? '').toLowerCase();
      const albumB = (b.albumTitle ?? '').toLowerCase();
      const genreA = (a.genre ?? '').toLowerCase();
      const genreB = (b.genre ?? '').toLowerCase();
      const releaseA = a.createdAt ? new Date(a.createdAt).getTime() : 0;
      const releaseB = b.createdAt ? new Date(b.createdAt).getTime() : 0;

      switch (this.sortBy) {
        case 'releaseDateAsc':
          return releaseA - releaseB;
        case 'releaseDateDesc':
          return releaseB - releaseA;
        case 'artistAsc':
          return artistA.localeCompare(artistB);
        case 'albumAsc':
          return albumA.localeCompare(albumB);
        case 'genreAsc':
          return genreA.localeCompare(genreB);
        default:
          return 0;
      }
    });
  }

  private updateSongs(rawSongs: SongCatalogResponse[]): void {
    const filteredByDate = this.releaseDateFilter
      ? rawSongs.filter((song) => (song.createdAt ?? '').slice(0, 10) === this.releaseDateFilter)
      : rawSongs;

    const sorted = this.sortSongs(filteredByDate);
    this.songs = sorted.map((song) => ({ ...song, isFavorite: false }));
    this.loadFavorites();
    this.loading = false;
  }

  private handleLoadError(): void {
    this.songs = [];
    this.loading = false;
    this.errorMessage = 'Unable to load songs';
  }
}
