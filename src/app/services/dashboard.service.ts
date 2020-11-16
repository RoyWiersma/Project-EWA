import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class DashboardService {
    constructor(private httpClient: HttpClient) {
    }

    public getUserfirstname(): Observable<any> {
        return this.httpClient.get<any>('http://localhost:8085/user{id}');
    }

    public getAppointment(): Observable<any> {
        return this.httpClient.get<any>('http://localhost:8085/appointment');
    }

}
