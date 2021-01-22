import { Injectable } from '@angular/core';
import {Observable, of} from 'rxjs';
import {environment} from '../../environments/environment';
import {Patient} from '../models/Patient';

@Injectable({
  providedIn: 'root'
})
export class DoctorMockService {

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
            response: this.patients
        });
    }
}
