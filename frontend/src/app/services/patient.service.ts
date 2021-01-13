import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Patient} from '../models/Patient';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class PatientService {

    constructor(private http: HttpClient) {
    }

    public updatePatient(patient: Patient, id: number): Observable<any> {
        Object.keys(patient).forEach(key => patient[key] === '' ? patient[key] = null : patient[key]);

        return this.http.put(`${environment.apiUrl}/patient/${id}`, JSON.stringify(patient), {
            headers: {
                authorization: localStorage.getItem('jwt') || null,
                'content-type': 'application/json'
            }
        });
    }
}
