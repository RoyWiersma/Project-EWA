import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Patient} from '../models/Patient';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class PatientService {

    private API_URL = 'http://localhost:8085/patient';

    constructor(private http: HttpClient) {
    }

    public updatePatient(patient: Patient, id: number): Observable<any> {
        return this.http.put(`${this.API_URL}/${id}`, JSON.stringify(patient), {
            headers: {
                authorization: localStorage.getItem('jwt') || null,
                'content-type': 'application/json'
            }
        });
    }
}
