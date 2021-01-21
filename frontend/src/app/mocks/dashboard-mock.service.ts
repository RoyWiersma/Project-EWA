import {Injectable} from '@angular/core';
import {Patient} from '../models/Patient';
import {Observable, of} from 'rxjs';
import {AgendaItem} from '../models/AgendaItem';

@Injectable({
    providedIn: 'root'
})

export class DashboardMockService {

    private patients: Patient[];
    private agendaItem: AgendaItem[];

    constructor() {
        this.patients = [];
        for (let i = 0; i < 10; i++) {
            this.patients.push(
                new Patient(i + 1, `Patient ${i + 1}`, 'test', 'van', `patient${i + 1}@test.nl`, '0123456', '0123456')
            );
        }
        this.agendaItem = [];
        for (let i = 0; i < 10; i++) {
            this.agendaItem.push(
                new AgendaItem()
            );
        }
    }

    public getPatient(id: number, firstname: string, lastname: string): Observable<any> {
        return of({
            patient: this.patients.find(patient => patient.id === id && patient.firstName === firstname && patient.lastName === lastname)
        });
    }
}
