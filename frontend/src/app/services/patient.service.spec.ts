import {inject, TestBed} from '@angular/core/testing';

import {PatientService} from './patient.service';
import {HttpClientModule} from '@angular/common/http';
import {Patient} from '../models/Patient';

describe('PatientService', () => {
    let service: jasmine.SpyObj<PatientService>;

    beforeEach(() => {
        const spy = jasmine.createSpyObj('PatientService', ['updatePatient']);

        TestBed.configureTestingModule({
            imports: [HttpClientModule],
            providers: [
                { provide: PatientService, useValue: spy }
            ]
        });

        service = TestBed.inject(PatientService) as jasmine.SpyObj<PatientService>;
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should call updatePatient() and return observable', () => {
        const testPatient = new Patient(1, 'test', 'test', 'test', 'test');
        service.updatePatient(testPatient, 1);

        expect(service.updatePatient).toHaveBeenCalledWith(testPatient, 1);
    });
});
