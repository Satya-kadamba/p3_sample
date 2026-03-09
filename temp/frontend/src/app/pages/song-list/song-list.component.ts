import { Component, OnInit } from '@angular/core';
import { SongService } from '../../services/song.service';

interface SongFilters {
  title: string;
  genre: string;
  album: string;
  releaseYear: string;
  sort: string;
}

@Component({
  selector: 'app-song-list',
  templateUrl: './song-list.component.html',
  styleUrls: ['./song-list.component.css']
})
export class SongListComponent implements OnInit {
  songs: any[] = [];
  artists: any[] = [];
  albums: any[] = [];
  loading = false;
  errorMessage = '';

  page = 0;
  size = 5;
  totalPages = 1;
  isSearchMode = false;

  searchKeyword = '';
  private searchSongs: any[] = [];

  filters: SongFilters = {
    title: '',
    genre: '',
    album: '',
    releaseYear: '',
    sort: ''
  };

  constructor(private songService: SongService) {}

  ngOnInit(): void {
    this.loadSongs();
  }

  loadSongs() {
    this.isSearchMode = false;
    this.loading = true;
    this.errorMessage = '';

    const normalizedFilters = this.getNormalizedFilters();
    if (this.filters.releaseYear && normalizedFilters.releaseYear === undefined) {
      this.loading = false;
      this.errorMessage = 'Release Year must be a valid 4-digit year.';
      return;
    }

    this.songService.getSongs(this.page, this.size, normalizedFilters).subscribe(response => {
      this.songs = response?.content ?? [];
      this.totalPages = response?.totalPages ?? 1;
      this.artists = [];
      this.albums = [];
      this.loading = false;
    }, () => {
      this.loading = false;
      this.errorMessage = 'Unable to load songs. Please try again.';
    });
  }

  onSearch(keyword: string) {
    this.searchKeyword = keyword?.trim() ?? '';

    if (!this.searchKeyword) {
      this.page = 0;
      this.loadSongs();
      return;
    }

    this.isSearchMode = true;
    this.loading = true;
    this.errorMessage = '';

    this.songService.search(this.searchKeyword).subscribe(response => {
      this.searchSongs = response?.songs ?? [];
      this.artists = response?.artists ?? [];
      this.albums = response?.albums ?? [];
      this.applySearchFiltersAndSort();
      this.loading = false;
    }, () => {
      this.loading = false;
      this.errorMessage = 'Search failed. Please try again.';
    });
  }

  applyFilters() {
    this.page = 0;

    if (this.isSearchMode) {
      this.applySearchFiltersAndSort();
      return;
    }

    this.loadSongs();
  }

  resetFilters() {
    this.filters = { title: '', genre: '', album: '', releaseYear: '', sort: '' };
    this.errorMessage = '';
    this.page = 0;

    if (this.isSearchMode) {
      this.applySearchFiltersAndSort();
      return;
    }

    this.loadSongs();
  }

  nextPage() {
    if (!this.isSearchMode && this.page + 1 < this.totalPages) {
      this.page++;
      this.loadSongs();
    }
  }

  previousPage() {
    if (!this.isSearchMode && this.page > 0) {
      this.page--;
      this.loadSongs();
    }
  }

  private getNormalizedFilters(): { title?: string; genre?: string; album?: string; releaseYear?: string; sort?: string } {
    const title = this.filters.title.trim();
    const genre = this.filters.genre.trim();
    const album = this.filters.album.trim();
    const releaseYear = this.filters.releaseYear.trim();
    const sort = this.filters.sort.trim();

    return {
      title: title || undefined,
      genre: genre || undefined,
      album: album || undefined,
      releaseYear: this.isValidYear(releaseYear) ? releaseYear : undefined,
      sort: sort || undefined
    };
  }

  private applySearchFiltersAndSort() {
    const normalizedFilters = this.getNormalizedFilters();

    if (this.filters.releaseYear && normalizedFilters.releaseYear === undefined) {
      this.errorMessage = 'Release Year must be a valid 4-digit year.';
      this.songs = [];
      return;
    }

    this.errorMessage = '';

    let filtered = [...this.searchSongs];

    if (normalizedFilters.title) {
      const title = normalizedFilters.title.toLowerCase();
      filtered = filtered.filter(song => (song.title ?? '').toLowerCase().includes(title));
    }

    if (normalizedFilters.genre) {
      const genre = normalizedFilters.genre.toLowerCase();
      filtered = filtered.filter(song => (song.genre ?? '').toLowerCase() === genre);
    }

    if (normalizedFilters.album) {
      const album = normalizedFilters.album.toLowerCase();
      filtered = filtered.filter(song => (song.albumName ?? '').toLowerCase().includes(album));
    }

    if (normalizedFilters.releaseYear) {
      filtered = filtered.filter(song => this.extractYear(song.releaseDate) === normalizedFilters.releaseYear);
    }

    this.songs = this.sortSongs(filtered, normalizedFilters.sort);
  }

  private sortSongs(list: any[], sortValue?: string): any[] {
    if (!sortValue) {
      return list;
    }

    const [field, direction] = sortValue.split(',');
    const isDesc = direction?.toLowerCase() === 'desc';

    return [...list].sort((a, b) => {
      if (field === 'releaseDate') {
        const left = new Date(a.releaseDate).getTime();
        const right = new Date(b.releaseDate).getTime();
        return isDesc ? right - left : left - right;
      }

      const left = (a.title ?? '').toString().toLowerCase();
      const right = (b.title ?? '').toString().toLowerCase();
      const compare = left.localeCompare(right);
      return isDesc ? -compare : compare;
    });
  }

  private isValidYear(year: string): boolean {
    return /^\d{4}$/.test(year);
  }

  private extractYear(dateValue: string): string {
    return (dateValue ?? '').toString().slice(0, 4);
  }
}
