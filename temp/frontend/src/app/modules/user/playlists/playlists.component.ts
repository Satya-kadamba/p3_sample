import { Component, OnInit } from '@angular/core';
import { PlaylistResponse, PlaylistService } from 'src/app/core/services/playlist.service';
import { SongCatalogResponse, SongLibraryService } from 'src/app/core/services/song-library.service';

@Component({
  selector: 'app-playlists',
  templateUrl: './playlists.component.html',
  styleUrls: ['./playlists.component.css'],
})
export class PlaylistsComponent implements OnInit {
  availableSongs: SongCatalogResponse[] = [];
  playlists: PlaylistResponse[] = [];

  newPlaylist = { name: '', description: '', isPublic: false };
  editDrafts: Record<number, { name: string; description: string; isPublic: boolean }> = {};
  songInput: Record<number, number | null> = {};

  constructor(
    private readonly playlistService: PlaylistService,
    private readonly songLibraryService: SongLibraryService,
  ) {}

  ngOnInit(): void {
    this.loadSongs();
  }

  loadSongs(): void {
    this.songLibraryService.getPublicSongs(0, 200).subscribe({
      next: (songsPage) => {
        this.availableSongs = songsPage.content;
        this.load();
      },
      error: () => {
        this.availableSongs = [];
        this.load();
      },
    });
  }

  load(): void {
    this.playlistService.getAll().subscribe({
      next: (res) => {
        this.playlists = res;

        this.playlists.forEach((playlist) => {
          this.editDrafts[playlist.id] = {
            name: playlist.name,
            description: playlist.description ?? '',
            isPublic: !!playlist.isPublic,
          };

          if (this.songInput[playlist.id] === undefined) {
            this.songInput[playlist.id] = this.availableSongs.length > 0 ? this.availableSongs[0].id : null;
          }
        });
      },
      error: () => {
        this.playlists = [];
      },
    });
  }

  canCreatePlaylist(): boolean {
    return this.newPlaylist.name.trim().length > 0;
  }

  create(): void {
    const name = this.newPlaylist.name.trim();
    if (!name) {
      return;
    }

    this.playlistService
      .create({
        name,
        description: this.newPlaylist.description.trim(),
        isPublic: this.newPlaylist.isPublic,
      })
      .subscribe({
        next: () => {
          this.newPlaylist = { name: '', description: '', isPublic: false };
          this.load();
        },
      });
  }

  update(playlistId: number): void {
    const draft = this.editDrafts[playlistId];
    this.playlistService
      .update(playlistId, {
        name: draft.name.trim(),
        description: draft.description.trim(),
        isPublic: draft.isPublic,
      })
      .subscribe({ next: () => this.load() });
  }

  delete(playlistId: number): void {
    this.playlistService.delete(playlistId).subscribe({ next: () => this.load() });
  }

  addSong(playlistId: number): void {
    const songId = this.songInput[playlistId];
    if (!songId) {
      return;
    }

    this.playlistService.addSong(playlistId, { songId }).subscribe({
      next: () => this.load(),
    });
  }

  removeSong(playlistId: number, songId: number): void {
    this.playlistService.removeSong(playlistId, songId).subscribe({
      next: () => this.load(),
    });
  }
}
