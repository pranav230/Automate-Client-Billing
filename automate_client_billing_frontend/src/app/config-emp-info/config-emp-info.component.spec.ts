import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigEmpInfoComponent } from './config-emp-info.component';

describe('ConfigEmpInfoComponent', () => {
  let component: ConfigEmpInfoComponent;
  let fixture: ComponentFixture<ConfigEmpInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfigEmpInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigEmpInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
