import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadResponseApprovedComponent } from './lead-response-approved.component';

describe('LeadResponseApprovedComponent', () => {
  let component: LeadResponseApprovedComponent;
  let fixture: ComponentFixture<LeadResponseApprovedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeadResponseApprovedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadResponseApprovedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
