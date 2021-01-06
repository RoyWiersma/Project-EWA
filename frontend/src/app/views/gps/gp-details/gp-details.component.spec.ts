import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GpDetailsComponent } from './gp-details.component';

describe('GpDetailsComponent', () => {
  let component: GpDetailsComponent;
  let fixture: ComponentFixture<GpDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GpDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GpDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
