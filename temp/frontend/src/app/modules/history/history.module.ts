import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { HistoryRoutingModule } from './history-routing.module';
import { ListeningHistoryComponent } from './listening-history.component';

@NgModule({
  declarations: [ListeningHistoryComponent],
  imports: [CommonModule, FormsModule, HistoryRoutingModule],
})
export class HistoryModule {}
