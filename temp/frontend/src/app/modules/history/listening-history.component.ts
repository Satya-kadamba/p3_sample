import { Component, OnInit } from '@angular/core';
import {
  ListeningHistoryItem,
  ListeningHistoryService,
  UserHistoryStatsResponse,
} from '../../core/service/listening-history.service';

@Component({
  selector: 'app-listening-history',
  templateUrl: './listening-history.component.html',
  styleUrls: ['./listening-history.component.css'],
})
export class ListeningHistoryComponent implements OnInit {
  historyItems: ListeningHistoryItem[] = [];
  topSongs: Array<Record<string, unknown>> = [];
  stats: UserHistoryStatsResponse = {
    totalSongsPlayed: 0,
    totalListeningTimeSeconds: 0,
    uniqueSongsPlayed: 0,
    topGenres: [],
  };

  durationDraft: Record<number, number> = {};

  loading = false;
  errorMessage = '';

  constructor(private readonly listeningHistoryService: ListeningHistoryService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.loadHistory();
    this.loadStats();
    this.loadTopSongs();
  }

  clearHistory(): void {
    this.loading = true;
    this.errorMessage = '';
    this.listeningHistoryService.clearHistory().subscribe({
      next: () => {
        this.loadAll();
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Unable to clear history right now.';
      },
    });
  }

  updateDuration(item: ListeningHistoryItem): void {
    const duration = this.durationDraft[item.id];
    if (!duration || duration < 0) {
      this.errorMessage = 'Enter a valid duration';
      return;
    }

    this.listeningHistoryService.updatePlayDuration(item.id, duration).subscribe({
      next: () => {
        this.loadHistory();
      },
      error: () => {
        this.errorMessage = 'Unable to update duration';
      },
    });
  }

  trackByHistoryId(index: number, item: ListeningHistoryItem): string {
    return `${item.id}-${index}`;
  }

  private loadHistory(): void {
    this.loading = true;
    this.errorMessage = '';
    this.listeningHistoryService.getRecentHistory(0, 50).subscribe({
      next: (items) => {
        this.historyItems = items;
        this.loading = false;
      },
      error: () => {
        this.historyItems = [];
        this.loading = false;
        this.errorMessage = 'Please log in to view listening history.';
      },
    });
  }

  private loadStats(): void {
    this.listeningHistoryService.getStats().subscribe({
      next: (res) => {
        this.stats = res;
      },
      error: () => {
        this.stats = {
          totalSongsPlayed: 0,
          totalListeningTimeSeconds: 0,
          uniqueSongsPlayed: 0,
          topGenres: [],
        };
      },
    });
  }

  private loadTopSongs(): void {
    this.listeningHistoryService.getTopSongs(10).subscribe({
      next: (rows) => {
        this.topSongs = rows;
      },
      error: () => {
        this.topSongs = [];
      },
    });
  }
}
