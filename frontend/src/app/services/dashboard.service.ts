import { Injectable } from '@angular/core';
import {HttpClient } from '@angular/common/http';
import {Observable } from 'rxjs';
import {AgendaItem} from '../models/AgendaItem';
import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})

export class DashboardService {
    public patientData: AgendaItem[];
    public doctorAppointment: AgendaItem[];

    constructor(private httpClient: HttpClient) {
    }

    // Getters used for doctor dashboard
    public getPatientNames(): Observable<any> {
        return this.httpClient.get(`${environment.apiUrl}/dashboard`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public getPatientAppointment(): Observable<any> {
        return this.httpClient.get(`${environment.apiUrl}/dashboard/appointment`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public getAppointmentsWithPatients(): AgendaItem[]{
        return this.patientData;
    }

    public getChat(): Observable<any> {
        return this.httpClient.get(`${environment.apiUrl}/dashboard/chat`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    // Getters used for Patient dashboard
    public getAppointmentWithDoctor(): AgendaItem[] {
        return this.doctorAppointment;
    }

    public getDoctorName(): Observable<any> {
        return this.httpClient.get(`${environment.apiUrl}/dashboard/doctor`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });

    }

}
