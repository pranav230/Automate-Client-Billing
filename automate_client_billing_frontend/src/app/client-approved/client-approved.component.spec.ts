import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientApprovedComponent } from './client-approved.component';

describe('ClientApprovedComponent', () => {
  let component: ClientApprovedComponent;
  let fixture: ComponentFixture<ClientApprovedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientApprovedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientApprovedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
