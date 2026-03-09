import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAlbumsComponent } from './manage-albums.component';

describe('ManageAlbumsComponent', () => {
  let component: ManageAlbumsComponent;
  let fixture: ComponentFixture<ManageAlbumsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageAlbumsComponent]
    });
    fixture = TestBed.createComponent(ManageAlbumsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
