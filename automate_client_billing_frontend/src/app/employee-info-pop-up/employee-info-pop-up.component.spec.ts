import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeInfoPopUpComponent } from './employee-info-pop-up.component';

describe('EmployeeInfoPopUpComponent', () => {
  let component: EmployeeInfoPopUpComponent;
  let fixture: ComponentFixture<EmployeeInfoPopUpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmployeeInfoPopUpComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeInfoPopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
