import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AgendaItem} from '../models/AgendaItem';

@Injectable({
    providedIn: 'root'
})
export class AgendaService {

    private API_URL = 'http://localhost:8085/agenda';

    constructor(private httpClient: HttpClient) { }

    public getAgendaItems(): Observable<any> {
        return this.httpClient.get(this.API_URL, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public saveAgendaItem(data: AgendaItem): Observable<any> {
        return (data.id === undefined) ? this.postRequest(data) : this.putRequest(data);
    }

    public deleteAgendaItem(id: number | string): Observable<any> {
        return this.httpClient.delete(`${this.API_URL}/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    private postRequest(data: AgendaItem): Observable<any> {
        const { title, description, patient, start, end, onLocation} = data;

        return this.httpClient.post(
            this.API_URL,
            JSON.stringify({title, description, patient: {id: patient}, start, end, onLocation}),
            {
                headers: {
                    authorization: localStorage.getItem('jwt') || null,
                    'content-type': 'application/json'
                }
            });
    }

    private putRequest(data: AgendaItem): Observable<any> {
        const { id, title, description, patient, start, end, onLocation} = data;
        const headers = new HttpHeaders();
        headers.set('Authorization', localStorage.getItem('jwt') || null);
        headers.set('content-type', 'application/json');

        return this.httpClient.put(
            `${this.API_URL}/${id}`,
            JSON.stringify({title, description, patient: {id: patient}, start, end, onLocation}),
            {
                headers: {
                    authorization: localStorage.getItem('jwt') || null,
                    'content-type': 'application/json'
                }
            });
    }
}
