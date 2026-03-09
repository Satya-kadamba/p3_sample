import { Component, OnInit } from '@angular/core';
import { SongResponse, SongService, SongVisibility } from 'src/app/core/services/song.service';

@Component({
  selector: 'app-my-songs',
  templateUrl: './my-songs.component.html',
  styleUrls: ['./my-songs.component.css'],
})
export class MySongsComponent implements OnInit {
  songs: SongResponse[] = [];
  editingSongId: number | null = null;
  editForm: Partial<SongResponse> = {
    title: '',
    genre: '',
    duration: 0,
    visibility: 'PUBLIC',
    coverImageUrl: '',
    fileUrl: '',
  };

  constructor(private readonly songService: SongService) {}

  ngOnInit(): void {
    this.loadSongs();
  }

  loadSongs(): void {
    this.songService.getSongs().subscribe({
      next: (res) => {
        this.songs = res;
      },
      error: () => {
        this.songs = [];
      },
    });
  }

  startEdit(song: SongResponse): void {
    this.editingSongId = song.id;
    this.editForm = {
      title: song.title,
      genre: song.genre,
      duration: song.duration,
      visibility: song.visibility,
      coverImageUrl: song.coverImageUrl,
      fileUrl: song.fileUrl,
      albumId: song.albumId,
    };
  }

  cancelEdit(): void {
    this.editingSongId = null;
  }

  updateSong(songId: number): void {
    this.songService
      .updateSong(songId, {
        title: this.editForm.title,
        genre: this.editForm.genre,
        duration: Number(this.editForm.duration),
        visibility: this.editForm.visibility as SongVisibility,
        coverImageUrl: this.editForm.coverImageUrl,
        fileUrl: this.editForm.fileUrl,
        albumId: this.editForm.albumId,
      })
      .subscribe({
        next: () => {
          this.editingSongId = null;
          this.loadSongs();
        },
      });
  }

  deleteSong(songId: number): void {
    if (!confirm('Are you sure to delete this song?')) {
      return;
    }

    this.songService.deleteSong(songId).subscribe({
      next: () => {
        this.loadSongs();
      },
    });
  }

  toggleVisibility(song: SongResponse): void {
    const newVisibility: SongVisibility = song.visibility === 'PUBLIC' ? 'PRIVATE' : 'PUBLIC';

    this.songService.updateVisibility(song.id, newVisibility).subscribe({
      next: (updatedSong) => {
        this.songs = this.songs.map((s) => (s.id === song.id ? updatedSong : s));
      },
    });
  }
}
