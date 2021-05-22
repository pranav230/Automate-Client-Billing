import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadResponseRejectedComponent } from './lead-response-rejected.component';

describe('LeadResponseRejectedComponent', () => {
  let component: LeadResponseRejectedComponent;
  let fixture: ComponentFixture<LeadResponseRejectedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeadResponseRejectedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadResponseRejectedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
