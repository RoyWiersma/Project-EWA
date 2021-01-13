import {Injectable} from '@angular/core';
import {Patient} from '../models/Patient';
import {Observable, of} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class PatientMockService {

    private patients: Patient[];

    constructor() {
        this.patients = [];
        for (let i = 0; i < 10; i++) {
            this.patients.push(
                new Patient(i + 1, `Patient ${i + 1}`, 'test', 'van', `patient${i + 1}@test.nl`, '0123456', '0123456')
            );
        }
    }

    public getPatients(): Observable<any> {
        return of({
            patients: this.patients
        });
    }

    public getPatient(id: number): Observable<any> {
        return of({
            patient: this.patients.find(patient => patient.id === id)
        });
    }
}
