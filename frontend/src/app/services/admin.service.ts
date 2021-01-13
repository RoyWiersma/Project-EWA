import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {GP} from '../models/GP';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
    private readonly API_URL = 'http://localhost:8085';
    private gps: GP[];

    constructor(private httpClient: HttpClient) {

    }

    public getAllGp(): Observable<any> {
        return this.httpClient.get(`${this.API_URL}/admin/get`, {
            headers: {authorization: localStorage.getItem('jwt') || null}
        });
    }

    public setAdmin(gp: GP): Observable<any> {
        return this.httpClient.patch(`${this.API_URL}/admin/setAdmin`, JSON.stringify(gp), {
            headers: { authorization: localStorage.getItem('jwt') || null, 'Content-Type': 'application/json' }
        });
    }

    public setNoAdmin(admin: GP): Observable<any> {
        return this.httpClient.patch(`${this.API_URL}/admin/setRemoveAdmin`, JSON.stringify(admin), {
            headers: { authorization: localStorage.getItem('jwt') || null, 'Content-Type': 'application/json' }
        });
    }
}
