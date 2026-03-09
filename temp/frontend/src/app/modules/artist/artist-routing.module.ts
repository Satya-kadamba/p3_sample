import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { UploadSongComponent } from './upload-song/upload-song.component';
import { ManageAlbumsComponent } from './manage-albums/manage-albums.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import { MySongsComponent } from './my-songs/my-songs.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'register', component: RegisterComponent },   //register
  { path: 'upload-song', component: UploadSongComponent },
  { path: 'manage-albums', component: ManageAlbumsComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'my-songs', component: MySongsComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArtistRoutingModule { }
