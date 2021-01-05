import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Patient} from '../models/Patient';

@Injectable({
    providedIn: 'root'
})
export class DoctorService {

    private API_URL = 'http://localhost:8085/gp';

    constructor(private http: HttpClient) {
    }

    public getPatients(): Observable<any> {
        return this.http.get(`${this.API_URL}/patients`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public getPatient(id: number): Observable<any> {
        return this.http.get(`${this.API_URL}/patient/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public addPatient(patient: Patient): Observable<any> {
        return this.http.post('http://localhost:8085/patient', JSON.stringify(patient), {
            headers: {
                'Content-Type': 'application/json',
                authorization: localStorage.getItem('jwt') || null
            }
        });
    }

    public deletePatient(id: number): Observable<any> {
        return this.http.delete(`http://localhost:8085/patient/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }
}
