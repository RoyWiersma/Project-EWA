import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {GP} from '../models/GP';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

    constructor(private httpClient: HttpClient) {

    }

    public getAllGp(): Observable<any> {
        return this.httpClient.get(`${environment.apiUrl}/admin/get`, {
            headers: {authorization: localStorage.getItem('jwt') || null}
        });
    }

    public setAdmin(gp: GP): Observable<any> {
        return this.httpClient.patch(`${environment.apiUrl}/admin/setAdmin`, JSON.stringify(gp), {
            headers: { authorization: localStorage.getItem('jwt') || null, 'Content-Type': 'application/json' }
        });
    }

    public setNoAdmin(admin: GP): Observable<any> {
        return this.httpClient.patch(`${environment.apiUrl}/admin/setRemoveAdmin`, JSON.stringify(admin), {
            headers: { authorization: localStorage.getItem('jwt') || null, 'Content-Type': 'application/json' }
        });
    }
}
