import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListeningHistoryComponent } from './listening-history.component';

const routes: Routes = [{ path: '', component: ListeningHistoryComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HistoryRoutingModule { }
