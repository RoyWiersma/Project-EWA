import { Injectable } from '@angular/core';
import {Observable, of} from 'rxjs';
import {GP} from '../models/GP';


@Injectable({
  providedIn: 'root'
})
export class GpMockService {

    private gps: GP[];

  constructor() {
      this.gps = [];
      for (let i = 0; i < 10; i++) {
          this.gps.push(
              new GP(i + 1, `Dokter ${i + 1}`, 'test', 'van', `Dokter${i + 1}@test.nl`, '0123456', '0123456')
          );
      }
  }

    public getGPs(): Observable<any> {
        return of({
            gps: this.gps
        });
    }

    public getGP(id: number): Observable<any> {
        return of({
            gp: this.gps.find(gp => gp.id === id)
        });
    }


}
