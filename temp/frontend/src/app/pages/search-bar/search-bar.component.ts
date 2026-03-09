import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SongService } from '../../services/song.service';

@Component({
  selector: 'app-search',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  results: any;
  keyword = '';

  constructor(
    private route: ActivatedRoute,
    private songService: SongService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.keyword = params['keyword'] ?? '';

      if (this.keyword) {
        this.search();
      }
    });
  }

  search(): void {
    const term = this.keyword.trim();
    if (!term) {
      this.results = [];
      return;
    }

    this.songService.search(term).subscribe((data) => {
      this.results = data;
    });
  }
}
