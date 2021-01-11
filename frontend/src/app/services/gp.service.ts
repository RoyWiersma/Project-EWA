import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {GP} from '../models/GP';
import {Patient} from "../models/Patient";

@Injectable({
  providedIn: 'root'
})
export class GpService {
    private API_URL_GPS = 'http://localhost:8085/gps';
    private API_URL_GP = 'http://localhost:8085/gp';

    constructor(private http: HttpClient) { }

    public getGPs(): Observable<any> {
        return this.http.get(`${this.API_URL_GP}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public getGP(id: number): Observable<any> {
        return this.http.get(`${this.API_URL_GP}/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        });
    }

    public addGP(gp: GP): Observable<any> {
        return this.http.post(`${this.API_URL_GPS}`, JSON.stringify(gp), {
            headers: {
                'Content-Type': 'application/json',
                authorization: localStorage.getItem('jwt') || null
            }
        });
    }

    public updateGP(gp: GP, id: number): Observable<any> {
        return this.http.put(`${this.API_URL_GPS}/${id}`, JSON.stringify(gp), {
            headers: {
                authorization: localStorage.getItem('jwt') || null,
                'content-type': 'application/json'
            }
        });
    }

    public deleteGP(id: number): Observable<any> {
        return this.http.delete(`${this.API_URL_GPS}/${id}`, {
            headers: {authorization: localStorage.getItem('jwt') || null}
        });
    }
}
