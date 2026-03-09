import { Component, ElementRef, HostListener } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { AuthService } from './core/services/auth.service';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  searchTerm = '';
  artistFilter = '';
  albumFilter = '';
  genreFilter = '';
  releaseDateFilter = '';
  sortBy = '';
  searchExpanded = false;

  constructor(
    public authService: AuthService,
    public router: Router,
    private elementRef: ElementRef,
  ) {
    this.syncSearchFromUrl();
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => this.syncSearchFromUrl());
  }

  onSearchFocus(): void {
    this.searchExpanded = true;
  }

  applySearchOptions(): void {
    this.onSearch();
  }

  @HostListener('document:click', ['$event'])
  handleDocumentClick(event: MouseEvent): void {
    const target = event.target as Node;
    const clickedInside = this.elementRef.nativeElement
      .querySelector('.nav-search-wrap')
      ?.contains(target);

    if (clickedInside) {
      return;
    }

    if (!this.searchTerm.trim() && !this.artistFilter.trim() && !this.albumFilter.trim() && !this.genreFilter.trim() && !this.releaseDateFilter.trim() && !this.sortBy) {
      this.searchExpanded = false;
    }
  }

  onSearch(): void {
    const q = this.searchTerm.trim();
    const artist = this.artistFilter.trim();
    const album = this.albumFilter.trim();
    const genre = this.genreFilter.trim();
    const releaseDate = this.releaseDateFilter.trim();
    const sort = this.sortBy.trim();
    const queryParams: { q?: string; artist?: string; album?: string; genre?: string; releaseDate?: string; sort?: string } = {};

    if (q) {
      queryParams.q = q;
    }
    if (artist) {
      queryParams.artist = artist;
    }
    if (album) {
      queryParams.album = album;
    }
    if (genre) {
      queryParams.genre = genre;
    }
    if (releaseDate) {
      queryParams.releaseDate = releaseDate;
    }
    if (sort) {
      queryParams.sort = sort;
    }

    this.router.navigate(['/browse'], {
      queryParams,
    });
  }

  clearSearchOptions(): void {
    this.searchTerm = '';
    this.artistFilter = '';
    this.albumFilter = '';
    this.genreFilter = '';
    this.releaseDateFilter = '';
    this.sortBy = '';
    this.searchExpanded = false;
    this.router.navigate(['/browse']);
  }

  private syncSearchFromUrl(): void {
    if (!this.router.url.startsWith('/browse')) {
      this.searchTerm = '';
      this.artistFilter = '';
      this.albumFilter = '';
      this.genreFilter = '';
      this.releaseDateFilter = '';
      this.sortBy = '';
      this.searchExpanded = false;
      return;
    }

    const parsedUrl = this.router.parseUrl(this.router.url);
    const q = parsedUrl.queryParams['q'];
    const artist = parsedUrl.queryParams['artist'];
    const album = parsedUrl.queryParams['album'];
    const genre = parsedUrl.queryParams['genre'];
    const releaseDate = parsedUrl.queryParams['releaseDate'];
    const sort = parsedUrl.queryParams['sort'];

    this.searchTerm = typeof q === 'string' ? q : '';
    this.artistFilter = typeof artist === 'string' ? artist : '';
    this.albumFilter = typeof album === 'string' ? album : '';
    this.genreFilter = typeof genre === 'string' ? genre : '';
    this.releaseDateFilter = typeof releaseDate === 'string' ? releaseDate : '';
    this.sortBy = typeof sort === 'string' ? sort : '';
    this.searchExpanded = !!(this.searchTerm || this.artistFilter || this.albumFilter || this.genreFilter || this.releaseDateFilter || this.sortBy);
  }
}
