import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SongService } from 'src/app/services/song.service';

@Component({
  selector: 'app-album-profile',
  templateUrl: './album-profile.component.html',
  styleUrls: ['./album-profile.component.css']
})
export class AlbumProfileComponent implements OnInit {

  album: any;

  constructor(
    private route: ActivatedRoute,
    private songService: SongService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.songService.getAlbumById(id)
      .subscribe({
        next: (data) => {
          this.album = data;
        },
        error: (err) => {
          console.error("Error loading album:", err);
        }
      });
  }
}
