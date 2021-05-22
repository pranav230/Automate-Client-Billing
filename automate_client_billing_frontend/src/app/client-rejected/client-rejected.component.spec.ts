import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientRejectedComponent } from './client-rejected.component';

describe('ClientRejectedComponent', () => {
  let component: ClientRejectedComponent;
  let fixture: ComponentFixture<ClientRejectedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientRejectedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientRejectedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
