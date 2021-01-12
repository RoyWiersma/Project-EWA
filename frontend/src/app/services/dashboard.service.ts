import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {AgendaItem} from '../models/AgendaItem';

@Injectable({
    providedIn: 'root'
})

export class DashboardService {
    public patientData: AgendaItem[];
    public doctorAppointment: AgendaItem[];

    private readonly API_URL = 'http://localhost:8085/dashboard';

    constructor(private httpClient: HttpClient) {
    }

    // Getters used for doctor dashboard
    public getPatientNames(): Observable<any> {
        return this.httpClient.get(this.API_URL, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public getPatientAppointment(): Observable<any> {
        return this.httpClient.get(`${this.API_URL}/appointment`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public getAppointmentsWithPatients(): AgendaItem[]{
        return this.patientData;
    }

    public getChat(): Observable<any> {
        return this.httpClient.get(`${this.API_URL}/chat`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    // Getters used for Patient dashboard
    public getAppointmentWithDoctor(): AgendaItem[] {
        return this.doctorAppointment;
    }

    public getDoctorName(): Observable<any> {
        return this.httpClient.get(`${this.API_URL}/doctor`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });

    }

}
