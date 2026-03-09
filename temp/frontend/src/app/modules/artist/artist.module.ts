import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { ArtistRoutingModule } from './artist-routing.module';

import { DashboardComponent } from './dashboard/dashboard.component';
import { UploadSongComponent } from './upload-song/upload-song.component';
import { ManageAlbumsComponent } from './manage-albums/manage-albums.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import { MySongsComponent } from './my-songs/my-songs.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    DashboardComponent,
    UploadSongComponent,
    ManageAlbumsComponent,
    ProfileComponent,
    RegisterComponent,
    MySongsComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ArtistRoutingModule
  ]
})
export class ArtistModule { }