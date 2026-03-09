import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SearchBarComponent } from './search-bar.component';
import { SongService } from '../../services/song.service';

describe('SearchBarComponent', () => {
  let component: SearchBarComponent;
  let fixture: ComponentFixture<SearchBarComponent>;
  let songServiceSpy: jasmine.SpyObj<SongService>;

  beforeEach(() => {
    songServiceSpy = jasmine.createSpyObj<SongService>('SongService', ['search']);
    songServiceSpy.search.and.returnValue(of([]));

    TestBed.configureTestingModule({
      declarations: [SearchBarComponent],
      imports: [FormsModule],
      providers: [
        { provide: SongService, useValue: songServiceSpy },
        { provide: ActivatedRoute, useValue: { queryParams: of({}) } }
      ]
    });

    fixture = TestBed.createComponent(SearchBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
