import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AgendaItem} from '../models/AgendaItem';
import { environment } from '../../environments/environment';


@Injectable({
    providedIn: 'root'
})
export class AgendaService {


    constructor(private httpClient: HttpClient) { }

    public getAgendaItems(): Observable<any> {
        return this.httpClient.get(`${environment.apiUrl}/agenda`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public saveAgendaItem(data: AgendaItem): Observable<any> {
        return (data.id === undefined) ? this.postRequest(data) : this.putRequest(data);
    }

    public deleteAgendaItem(id: number | string): Observable<any> {
        return this.httpClient.delete(`${environment.apiUrl}/agenda/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public postRequest(data: AgendaItem): Observable<any> {
        const { title, description, patient, start, end, onLocation} = data;

        return this.httpClient.post(
            `${environment.apiUrl}/agenda`,
            JSON.stringify({title, description, patient: {id: patient}, start, end, onLocation}),
            {
                headers: {
                    authorization: localStorage.getItem('jwt') || null,
                    'content-type': 'application/json'
                }
            });
    }

    private putRequest(data: AgendaItem): Observable<any> {
        const { id, title, description, start, end, onLocation} = data;
        const headers = new HttpHeaders();
        headers.set('Authorization', localStorage.getItem('jwt') || null);
        headers.set('content-type', 'application/json');

        return this.httpClient.put(
            `${environment.apiUrl}/agenda/${id}`,
            JSON.stringify({title, description, start, end, onLocation}),
            {
                headers: {
                    authorization: localStorage.getItem('jwt') || null,
                    'content-type': 'application/json'
                }
            });
    }
}
