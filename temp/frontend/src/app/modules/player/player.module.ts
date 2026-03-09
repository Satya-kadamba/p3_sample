import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlayerRoutingModule } from './player-routing.module';
import { MusicPlayerComponent } from '../../shared/components/music-player/music-player.component';

@NgModule({
  declarations: [MusicPlayerComponent],
  imports: [
    CommonModule,
    PlayerRoutingModule
  ]
})
export class PlayerModule { }
