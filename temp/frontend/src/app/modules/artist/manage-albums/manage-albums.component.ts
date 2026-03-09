import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlbumResponse, AlbumService } from 'src/app/core/services/album.service';
import { SongResponse, SongService } from 'src/app/core/services/song.service';

@Component({
  selector: 'app-manage-albums',
  templateUrl: './manage-albums.component.html',
  styleUrls: ['./manage-albums.component.css'],
})
export class ManageAlbumsComponent implements OnInit {
  albumForm: FormGroup;
  albums: AlbumResponse[] = [];
  songs: SongResponse[] = [];

  editingAlbumId: number | null = null;
  albumEditForm: { title: string; description: string; releaseDate: string; coverImageUrl: string } = {
    title: '',
    description: '',
    releaseDate: '',
    coverImageUrl: '',
  };

  constructor(
    private readonly fb: FormBuilder,
    private readonly albumService: AlbumService,
    private readonly songService: SongService
  ) {
    this.albumForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      coverImageUrl: [''],
      releaseDate: [''],
    });
  }

  ngOnInit(): void {
    this.loadAlbums();
    this.loadSongs();
  }

  loadAlbums(): void {
    this.albumService.getAlbums().subscribe({
      next: (res) => {
        this.albums = res;
      },
      error: () => {
        this.albums = [];
      },
    });
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

  createAlbum(): void {
    if (this.albumForm.invalid) {
      this.albumForm.markAllAsTouched();
      return;
    }

    this.albumService.createAlbum(this.albumForm.value).subscribe({
      next: () => {
        this.albumForm.reset();
        this.loadAlbums();
      },
    });
  }

  addToAlbum(songId: number, albumId: number): void {
    this.songService.updateSong(songId, { albumId }).subscribe({
      next: () => {
        this.loadAlbums();
        this.loadSongs();
      },
    });
  }

  removeFromAlbum(songId: number): void {
    this.songService.updateSong(songId, { albumId: undefined }).subscribe({
      next: () => {
        this.loadAlbums();
        this.loadSongs();
      },
    });
  }

  startAlbumEdit(album: AlbumResponse): void {
    this.editingAlbumId = album.id;
    this.albumEditForm = {
      title: album.title,
      description: album.description ?? '',
      releaseDate: album.releaseDate ?? '',
      coverImageUrl: album.coverImageUrl ?? '',
    };
  }

  cancelAlbumEdit(): void {
    this.editingAlbumId = null;
  }

  updateAlbum(albumId: number): void {
    this.albumService.updateAlbum(albumId, this.albumEditForm).subscribe({
      next: () => {
        this.editingAlbumId = null;
        this.loadAlbums();
      },
    });
  }

  deleteAlbum(albumId: number): void {
    if (!confirm('Are you sure to delete this album?')) {
      return;
    }

    this.albumService.deleteAlbum(albumId).subscribe({
      next: () => {
        this.loadAlbums();
      },
    });
  }
}
