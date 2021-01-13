import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PatientsComponent} from './patients.component';
import {HttpClientModule} from '@angular/common/http';
import {DoctorService} from '../../services/doctor.service';
import {PatientMockService} from '../../mocks/patient-mock.service';
import {DebugElement} from '@angular/core';

describe('PatientsComponent', () => {
    let component: PatientsComponent;
    let fixture: ComponentFixture<PatientsComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [PatientsComponent],
            imports: [
                HttpClientModule,
            ],
            providers: [
                {
                    provide: DoctorService,
                    useClass: PatientMockService,
                }
            ]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(PatientsComponent);
            component = fixture.componentInstance;
            fixture.detectChanges();

        });
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should render 10 items in the table', () => {
        const dbElement: DebugElement = fixture.debugElement;
        const tableRows: HTMLTableRowElement[] = dbElement.nativeElement.querySelectorAll('tbody tr');

        expect(tableRows.length).toEqual(10);
    });

    it('should fill table row with correct data', () => {
        const dbElement: DebugElement = fixture.debugElement;
        const tableItems: HTMLTableDataCellElement[] = dbElement.nativeElement.querySelector('tbody tr').childNodes;

        expect(tableItems.length).toBeGreaterThanOrEqual(4);
        expect(tableItems[0].textContent).toEqual('test, Patient 1 van');
        expect(tableItems[1].textContent).toEqual('patient1@test.nl');
        expect(tableItems[2].textContent).toEqual('0123456');
        expect(tableItems[3].textContent).toEqual('0123456');
    });
});
