import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SongRequest, SongService, SongVisibility } from 'src/app/core/services/song.service';

@Component({
  selector: 'app-upload-song',
  templateUrl: './upload-song.component.html',
  styleUrls: ['./upload-song.component.css'],
})
export class UploadSongComponent {
  uploadForm: FormGroup;
  errorMessage = '';
  successMessage = '';

  constructor(
    private readonly fb: FormBuilder,
    private readonly songService: SongService
  ) {
    this.uploadForm = this.fb.group({
      title: ['', Validators.required],
      genre: [''],
      duration: [180, [Validators.required, Validators.min(1)]],
      visibility: ['PUBLIC', Validators.required],
      fileUrl: ['', Validators.required],
      coverImageUrl: [''],
      albumId: [''],
    });
  }

  uploadSong(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (this.uploadForm.invalid) {
      this.uploadForm.markAllAsTouched();
      return;
    }

    const form = this.uploadForm.value;
    const payload: SongRequest = {
      title: form.title,
      genre: form.genre || undefined,
      duration: Number(form.duration),
      visibility: form.visibility as SongVisibility,
      fileUrl: form.fileUrl,
      coverImageUrl: form.coverImageUrl || undefined,
      albumId: form.albumId ? Number(form.albumId) : undefined,
    };

    this.songService.uploadSong(payload).subscribe({
      next: () => {
        this.successMessage = 'Song uploaded successfully';
        this.uploadForm.reset({ visibility: 'PUBLIC', duration: 180 });
      },
      error: (err) => {
        this.errorMessage = err?.error?.message || 'Upload failed';
      },
    });
  }
}
