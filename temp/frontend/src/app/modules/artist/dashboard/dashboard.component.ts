import { Component, OnInit } from '@angular/core';
import {
  AnalyticsOverviewResponse,
  AnalyticsService,
  ListeningTrendResponse,
  SongPerformanceResponse,
  TopListenerResponse,
  TopSongsResponse,
} from 'src/app/core/services/analytics.service';

@Component({
  selector: 'app-artist-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  artistId = Number(localStorage.getItem('artistId')) || 1;

  overview?: AnalyticsOverviewResponse;
  songs: SongPerformanceResponse[] = [];
  topSongs: SongPerformanceResponse[] = [];
  topListeners: TopListenerResponse[] = [];
  trends: ListeningTrendResponse['trends'] = [];

  period: 'ALL_TIME' | 'DAILY' | 'WEEKLY' | 'MONTHLY' = 'ALL_TIME';
  health = '';

  constructor(private readonly analyticsService: AnalyticsService) {}

  ngOnInit(): void {
    this.loadOverview();
    this.loadSongs();
    this.loadTopSongs();
    this.loadTrends();
    this.loadTopListeners();
    this.loadHealth();
  }

  loadOverview(): void {
    this.analyticsService.getOverview(this.artistId, this.period).subscribe({
      next: (data) => (this.overview = data),
    });
  }

  loadSongs(): void {
    this.analyticsService.getSongPerformance(this.artistId).subscribe({
      next: (data) => (this.songs = data),
    });
  }

  loadTopSongs(): void {
    this.analyticsService.getTopSongs(this.artistId, 10, this.period).subscribe({
      next: (data: TopSongsResponse) => (this.topSongs = data.songs),
    });
  }

  loadTrends(): void {
    const end = new Date();
    const start = new Date();
    start.setDate(end.getDate() - 30);

    this.analyticsService
      .getTrends(this.artistId, start.toISOString().slice(0, 10), end.toISOString().slice(0, 10))
      .subscribe({
        next: (data) => (this.trends = data.trends),
      });
  }

  onPeriodChange(value: string): void {
    const normalized = (value || 'ALL_TIME').toUpperCase();
    if (normalized === 'DAILY' || normalized === 'WEEKLY' || normalized === 'MONTHLY' || normalized === 'ALL_TIME') {
      this.period = normalized;
    }
    this.loadOverview();
    this.loadTopSongs();
  }

  loadTopListeners(): void {
    this.analyticsService.getTopListeners(this.artistId, 10).subscribe({
      next: (data) => (this.topListeners = data),
    });
  }

  loadHealth(): void {
    this.analyticsService.getHealth().subscribe({
      next: (res) => (this.health = res),
      error: () => (this.health = 'Unavailable'),
    });
  }
}
