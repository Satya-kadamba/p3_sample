import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SongCatalogResponse, SongLibraryService } from 'src/app/core/services/song-library.service';

@Component({
  selector: 'app-song-detail',
  templateUrl: './song-detail.component.html',
  styleUrls: ['./song-detail.component.css'],
})
export class SongDetailComponent implements OnInit {
  song: SongCatalogResponse | null = null;
  loading = true;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly songLibraryService: SongLibraryService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.loading = false;
      return;
    }

    this.songLibraryService.getSongById(id).subscribe({
      next: (song) => {
        this.song = song;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      },
    });
  }
}
