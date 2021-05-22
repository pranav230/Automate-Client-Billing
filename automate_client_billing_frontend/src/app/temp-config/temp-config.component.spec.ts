import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TempConfigComponent } from './temp-config.component';

describe('TempConfigComponent', () => {
  let component: TempConfigComponent;
  let fixture: ComponentFixture<TempConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TempConfigComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TempConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
