import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlbumProfileComponent } from './album-profile.component';

describe('AlbumProfileComponent', () => {
  let component: AlbumProfileComponent;
  let fixture: ComponentFixture<AlbumProfileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AlbumProfileComponent]
    });
    fixture = TestBed.createComponent(AlbumProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
