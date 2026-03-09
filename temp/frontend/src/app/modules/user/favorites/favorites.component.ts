import { Component, OnInit } from '@angular/core';
import { FavoriteResponse, FavoriteService } from 'src/app/core/services/favorite.service';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css'],
})
export class FavoritesComponent implements OnInit {
  favorites: FavoriteResponse[] = [];
  checkSongId: number | null = null;
  checkResult = '';

  constructor(private readonly favoriteService: FavoriteService) {}

  ngOnInit(): void {
    this.loadFavorites();
  }

  loadFavorites(): void {
    this.favoriteService.get().subscribe({
      next: (rows) => {
        this.favorites = rows;
      },
      error: () => {
        this.favorites = [];
      },
    });
  }

  remove(songId: number): void {
    const previous = [...this.favorites];
    this.favorites = this.favorites.filter((song) => song.songId !== songId);

    this.favoriteService.remove(songId).subscribe({
      next: () => {},
      error: () => {
        this.favorites = previous;
      },
    });
  }

  checkFavorite(): void {
    if (!this.checkSongId) {
      this.checkResult = 'Enter a valid song ID';
      return;
    }

    this.favoriteService.check(this.checkSongId).subscribe({
      next: (res) => {
        this.checkResult = res.isFavorite ? 'Song is in favorites' : 'Song is not in favorites';
      },
      error: () => {
        this.checkResult = 'Failed to check favorite status';
      },
    });
  }
}
