import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {User} from '../models/User';
import {Patient} from '../models/Patient';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})

export class DossierService {
    public patientDossier = new Patient();
    public newPatient = new Patient();

    constructor(private httpClient: HttpClient) {

    }

    // Getters used for doctor dashboard
    public getPatientDossier(): Observable<any> {
        return this.httpClient.get(`${environment.apiUrl}/dossier/patient`, {
            headers: {authorization: localStorage.getItem('jwt') || null}
        });
    }

    public postPassword(password: string): Observable<any> {
        this.newPatient.password = password;
        const tempPatient: User = this.newPatient;
        return this.httpClient.post(
            `${environment.apiUrl}/dossier/login`,
            JSON.stringify(tempPatient),
            {
                headers: {
                    authorization: localStorage.getItem('jwt') || null,
                    'Content-Type': 'application/json'
                }
            });
    }
    public getAllByPatient(id: number): Observable<any> {
        return this.httpClient.get(`${environment.apiUrl}/gp/patient/${id}/dossier`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public getMediaItem(id: number): Observable<any> {
        return this.httpClient.get(`${environment.apiUrl}/dossier/medical-media/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public saveMediaItem(formData: FormData, patientId: number): Observable<any> {
        return this.httpClient.post(`${environment.apiUrl}/gp/patient/${patientId}/dossier`, formData, {
            headers: {
                Accept: 'application/json',
                authorization: localStorage.getItem('jwt') || null
            }
        });
    }

    public deleteItemById(id: number): Observable<any> {
        return this.httpClient.delete(`${environment.apiUrl}/dossier/medical-media/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }
}
