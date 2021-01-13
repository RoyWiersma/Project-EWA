import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {GP} from '../models/GP';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GpService {

    constructor(private http: HttpClient) { }

    public getGPs(): Observable<any> {
        return this.http.get(`${environment.apiUrl}/gp`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public getGP(id: number): Observable<any> {
        return this.http.get(`${environment.apiUrl}/gp/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public addGP(gp: GP): Observable<any> {
        return this.http.post(`${environment.apiUrl}/gps`, JSON.stringify(gp), {
            headers: {
                'Content-Type': 'application/json',
                authorization: localStorage.getItem('jwt') || null
            }
        });
    }

    public updateGP(gp: GP, id: number): Observable<any> {
        Object.keys(gp).forEach(key => gp[key] === '' ? gp[key] = null : gp[key]);

        return this.http.put(`${environment.apiUrl}/gps/${id}`, JSON.stringify(gp), {
            headers: {
                authorization: localStorage.getItem('jwt') || null,
                'content-type': 'application/json'
            }
        });
    }

    public deleteGP(id: number): Observable<any> {
        return this.http.delete(`${environment.apiUrl}/gps/${id}`, {
            headers: {authorization: localStorage.getItem('jwt') || null}
        });
    }
}
