import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GpsComponent } from './gps.component';
import {GpService} from '../../services/gp.service';
import {GpMockService} from '../../mocks/gp-mock.service';
import {DebugElement} from '@angular/core';


describe('GpsComponent', () => {
  let component: GpsComponent;
  let fixture: ComponentFixture<GpsComponent>;


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GpsComponent ],

        providers: [
            {
                provide: GpService,
                useClass: GpMockService,
            }
        ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GpsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render 10 items in the table', () => {
        const dbElement: DebugElement = fixture.debugElement;
        const tableRows: HTMLTableRowElement[] = dbElement.nativeElement.querySelectorAll('tbody tr');

        expect(tableRows.length).toEqual(10);
  });
  it('should open the modal', async(() => {
        spyOn(component, 'openModal');

        const button = fixture.debugElement.nativeElement.querySelector('button');
        button.click();

        fixture.whenStable().then(() => {
            expect(component.openModal).toHaveBeenCalled();
        });
    }));
  // it('should delete 1 GP', async(() => {
  //       spyOn(window, 'confirm').and.returnValue(true);
  //       const dbElement: DebugElement = fixture.debugElement;
  //       const button: HTMLTableRowElement[] = dbElement.nativeElement.querySelectorAll('#deleteGP');
  //       button[0].click();
  //
  //       const tableRows: HTMLTableRowElement[] = dbElement.nativeElement.querySelectorAll('tbody tr');
  //       fixture.whenStable().then(() => {
  //           expect(tableRows.length).toEqual(9);
  //       });
  //   }));
});
