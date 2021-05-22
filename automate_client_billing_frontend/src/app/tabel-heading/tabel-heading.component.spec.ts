import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabelHeadingComponent } from './tabel-heading.component';

describe('TabelHeadingComponent', () => {
  let component: TabelHeadingComponent;
  let fixture: ComponentFixture<TabelHeadingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabelHeadingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TabelHeadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
