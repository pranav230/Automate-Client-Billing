import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadResponseComponent } from './lead-response.component';

describe('LeadResponseComponent', () => {
  let component: LeadResponseComponent;
  let fixture: ComponentFixture<LeadResponseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeadResponseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadResponseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
