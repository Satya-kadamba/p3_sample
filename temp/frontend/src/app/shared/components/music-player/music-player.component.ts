import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Song, SongService } from 'src/app/core/service/song.service';
import { ListeningHistoryService, NowPlayingResponse } from 'src/app/core/service/listening-history.service';

@Component({
  selector: 'app-music-player',
  templateUrl: './music-player.component.html',
  styleUrls: ['./music-player.component.css'],
})
export class MusicPlayerComponent implements OnInit, OnDestroy {
  songs: Song[] = [];
  currentSongIndex = 0;
  isPlaying = false;
  currentTime = 0;
  duration = 0;
  volume = 1;
  loadError = '';
  nowPlaying: NowPlayingResponse | null = null;

  private readonly audio = new Audio();
  private hasRecordedCurrentSong = false;
  private isRecordingCurrentSong = false;

  private readonly onTimeUpdate = (): void => {
    this.currentTime = this.audio.currentTime;
    this.duration = this.audio.duration || 0;
  };

  private readonly onLoadedMetadata = (): void => {
    this.duration = this.audio.duration || 0;
  };

  private readonly onEnded = (): void => {
    this.next();
  };

  constructor(
    private readonly songService: SongService,
    private readonly listeningHistoryService: ListeningHistoryService,
    private readonly route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.audio.volume = this.volume;
    this.audio.addEventListener('timeupdate', this.onTimeUpdate);
    this.audio.addEventListener('loadedmetadata', this.onLoadedMetadata);
    this.audio.addEventListener('ended', this.onEnded);

    this.listeningHistoryService.getNowPlaying().subscribe({
      next: (data) => (this.nowPlaying = data),
      error: () => {
        this.nowPlaying = null;
      },
    });

    this.songService.getAllSongs().subscribe({
      next: (songs) => {
        this.songs = songs;
        if (this.songs.length === 0) {
          return;
        }

        const querySongId = Number(this.route.snapshot.queryParamMap.get('songId'));
        const indexFromQuery = this.songs.findIndex((song) => song.id === querySongId);
        const initialIndex = indexFromQuery >= 0 ? indexFromQuery : 0;

        this.loadSong(initialIndex);

        if (indexFromQuery >= 0) {
          this.startPlayback();
        }
      },
      error: () => {
        this.songs = [];
        this.loadError = 'Unable to load songs. Please try again.';
      },
    });
  }

  ngOnDestroy(): void {
    this.audio.pause();
    this.audio.removeEventListener('timeupdate', this.onTimeUpdate);
    this.audio.removeEventListener('loadedmetadata', this.onLoadedMetadata);
    this.audio.removeEventListener('ended', this.onEnded);
  }

  get currentSong(): Song | null {
    return this.songs[this.currentSongIndex] ?? null;
  }

  playPause(): void {
    if (!this.currentSong) {
      return;
    }

    if (this.isPlaying) {
      this.audio.pause();
      this.isPlaying = false;
      return;
    }

    this.startPlayback();
  }

  next(): void {
    if (this.currentSongIndex >= this.songs.length - 1) {
      this.audio.pause();
      this.isPlaying = false;
      return;
    }

    this.loadSong(this.currentSongIndex + 1);
    this.startPlayback();
  }

  previous(): void {
    if (this.currentSongIndex <= 0) {
      return;
    }

    this.loadSong(this.currentSongIndex - 1);
    this.startPlayback();
  }

  seek(event: Event): void {
    const input = event.target as HTMLInputElement | null;
    if (!input) {
      return;
    }

    this.audio.currentTime = Number(input.value);
  }

  changeVolume(event: Event): void {
    const input = event.target as HTMLInputElement | null;
    if (!input) {
      return;
    }

    const value = Number(input.value);
    this.volume = Math.min(1, Math.max(0, value));
    this.audio.volume = this.volume;
  }

  playSong(index: number): void {
    if (index < 0 || index >= this.songs.length) {
      return;
    }

    this.loadSong(index);
    this.startPlayback();
  }

  isCurrentSong(index: number): boolean {
    return index === this.currentSongIndex;
  }

  formatTime(time: number): string {
    if (!Number.isFinite(time) || time < 0) {
      return '0:00';
    }

    const minutes = Math.floor(time / 60);
    const seconds = Math.floor(time % 60);
    return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
  }

  private loadSong(index: number): void {
    this.currentSongIndex = index;
    this.currentTime = 0;
    this.duration = 0;
    this.isPlaying = false;
    this.hasRecordedCurrentSong = false;
    this.audio.src = this.songs[index].url;
    this.audio.load();
  }

  private startPlayback(): void {
    this.recordPlayIfNeeded();
    this.audio
      .play()
      .then(() => {
        this.isPlaying = true;
      })
      .catch(() => {
        this.isPlaying = false;
      });
  }

  private recordPlayIfNeeded(): void {
    if (this.hasRecordedCurrentSong || this.isRecordingCurrentSong || !this.currentSong) {
      return;
    }

    this.isRecordingCurrentSong = true;
    this.listeningHistoryService.recordPlay(this.currentSong.id).subscribe({
      next: () => {
        this.hasRecordedCurrentSong = true;
        this.isRecordingCurrentSong = false;
      },
      error: () => {
        this.isRecordingCurrentSong = false;
        this.loadError = 'Play history is unavailable until you log in.';
      },
    });
  }
}
