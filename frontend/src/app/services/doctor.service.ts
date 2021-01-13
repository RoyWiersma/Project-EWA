import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Patient} from '../models/Patient';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class DoctorService {

    constructor(private http: HttpClient) {
    }

    public getPatients(): Observable<any> {
        return this.http.get(`${environment.apiUrl}/gp/patients`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public getPatient(id: number): Observable<any> {
        return this.http.get(`${environment.apiUrl}/gp/patient/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public addPatient(patient: Patient): Observable<any> {
        return this.http.post(`${environment.apiUrl}/patient`, JSON.stringify(patient), {
            headers: {
                'Content-Type': 'application/json',
                authorization: localStorage.getItem('jwt') || null
            }
        });
    }

    public deletePatient(id: number): Observable<any> {
        return this.http.delete(`${environment.apiUrl}/patient/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }
}
