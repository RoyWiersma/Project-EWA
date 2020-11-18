import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Patient} from '../models/patient';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class PatientService {

    private API_URL = 'http://localhost:8085/user';
    private patients: Patient[];

    constructor(private http: HttpClient) {
        this.patients = [];
        this.getAllPatients().subscribe(items => {
            items.forEach(item => {
                this.patients.push(item);
            });
        });
    }

    public findAll(): Patient[] {
        return this.patients;
    }

    private getAllPatients(): Observable<any> {
        return this.http.get(this.API_URL, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }
}
