import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadResponsePendingComponent } from './lead-response-pending.component';

describe('LeadResponsePendingComponent', () => {
  let component: LeadResponsePendingComponent;
  let fixture: ComponentFixture<LeadResponsePendingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeadResponsePendingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadResponsePendingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
