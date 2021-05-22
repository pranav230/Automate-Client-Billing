import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpansionCardInfoComponent } from './expansion-card-info.component';

describe('ExpansionCardInfoComponent', () => {
  let component: ExpansionCardInfoComponent;
  let fixture: ComponentFixture<ExpansionCardInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExpansionCardInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpansionCardInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
