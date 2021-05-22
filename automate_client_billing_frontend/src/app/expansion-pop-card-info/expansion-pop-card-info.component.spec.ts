import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpansionPopCardInfoComponent } from './expansion-pop-card-info.component';

describe('ExpansionPopCardInfoComponent', () => {
  let component: ExpansionPopCardInfoComponent;
  let fixture: ComponentFixture<ExpansionPopCardInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExpansionPopCardInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpansionPopCardInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
