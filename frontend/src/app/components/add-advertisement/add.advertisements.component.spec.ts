import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAdvertisementsComponent } from './add.advertisements.component';

describe('AdvertisementsComponent', () => {
  let component: AddAdvertisementsComponent;
  let fixture: ComponentFixture<AddAdvertisementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAdvertisementsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAdvertisementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
