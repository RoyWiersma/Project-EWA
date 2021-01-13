import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PatientDetailsComponent} from './patient-details.component';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {of} from 'rxjs';
import {DoctorService} from '../../../services/doctor.service';
import {PatientMockService} from '../../../mocks/patient-mock.service';
import objectContaining = jasmine.objectContaining;
import {FormsModule} from '@angular/forms';
import {DebugElement} from '@angular/core';

describe('PatientDetailsComponent', () => {
    let component: PatientDetailsComponent;
    let fixture: ComponentFixture<PatientDetailsComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [
                PatientDetailsComponent
            ],
            imports: [
                RouterModule,
                FormsModule,
                HttpClientModule,
            ],
            providers: [
                {
                    provide: DoctorService,
                    useClass: PatientMockService,
                },
                {
                    provide: ActivatedRoute,
                    useValue: {
                        params: of({ id: 1 })
                    }
                }
            ]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(PatientDetailsComponent);
            fixture.detectChanges();

            component = fixture.componentInstance;
        });
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should load correct patient detail', () => {
        expect(component.patient).toEqual(objectContaining({
            firstName: 'Patient 1',
            lastName: 'test',
            middleName: 'van',
            email: 'patient1@test.nl',
            phoneNumber: '0123456',
            mobileNumber: '0123456'
        }));
    });

    it('should fill patient edit form with correct data', () => {

        expect(component.editedPatient).toEqual(objectContaining({
            firstName: 'Patient 1',
            lastName: 'test',
            middleName: 'van',
            email: 'patient1@test.nl',
            phoneNumber: '0123456',
            mobileNumber: '0123456'
        }));
    });

    it('should disable submit button on patient form when no changes were made', () => {
        const deElement: DebugElement = fixture.debugElement;
        const submitButton: HTMLButtonElement = deElement.nativeElement.querySelector('#submit-edit-profile');

        expect(submitButton.disabled).toBeTrue();
    });
});
