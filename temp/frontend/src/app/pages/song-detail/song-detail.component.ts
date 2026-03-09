import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SongService } from '../../services/song.service';

@Component({
  selector: 'app-song-detail',
  templateUrl: './song-detail.component.html'
})
export class SongDetailComponent implements OnInit {

  song: any;

  constructor(
    private route: ActivatedRoute,
    private songService: SongService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.songService.getSongById(id)
      .subscribe(data => this.song = data);
  }
}