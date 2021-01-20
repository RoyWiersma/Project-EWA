import { Injectable } from '@angular/core';
import {Observable, of} from 'rxjs';
import {GP} from '../models/GP';

@Injectable({
  providedIn: 'root'
})
export class AdminMockService {

    private gps: GP[];

    constructor() {
        this.gps = [];
        for (let i = 0; i < 10; i++) {
            this.gps.push(
                new GP(i + 1,
                    `GP ${i + 1}`,
                    'test',
                    'van',
                    `gp${i + 1}@test.nl`,
                    '0123456',
                    '0123456',
                    (i % 2 === 0 ? true : false),
                    'test')
            );
        }
    }
    public getAllGp(): Observable<any> {
        return of(this.gps);
    }

    public setAdmin(gp: GP): Observable<any> {
        this.gps.find(temp => gp.id === temp.id).isAdmin = true;
        return null;
    }

    public setNoAdmin(admin: GP): Observable<any> {
        this.gps.find(temp => admin.id === temp.id).isAdmin = false;
        return null;
    }

}
