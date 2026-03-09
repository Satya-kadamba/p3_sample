import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { PlaylistsRoutingModule } from './playlists-routing.module';
import { PlaylistsComponent } from './playlists.component';


@NgModule({
  declarations: [
    PlaylistsComponent
  ],
  imports: [
    CommonModule,
    PlaylistsRoutingModule,
    FormsModule
  ]
})
export class PlaylistsModule { }
