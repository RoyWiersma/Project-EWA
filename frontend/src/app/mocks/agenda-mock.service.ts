import { Injectable } from '@angular/core';
import {GP} from '../models/GP';
import {Observable, of} from 'rxjs';
import {AgendaItem} from '../models/AgendaItem';
import {Patient} from '../models/Patient';

@Injectable({
    providedIn: 'root'
})
export class AgendaMockService {

    private agendaItems: AgendaItem[];

    constructor() {
        this.agendaItems = [];
        this.agendaItems.push(
                new AgendaItem(
                    1,
                    new Date(),
                    new Date(),
                    true,
                    new GP(),
                    new Patient(),
                    'title',
                    'description'
            ));
        this.agendaItems.push(
            new AgendaItem(
                2,
                new Date(),
                new Date(),
                true,
                new GP(),
                new Patient(),
                'title',
                'description'
            ));
    }

    public getAgendaItems(): Observable<any> {
        return of(this.agendaItems);
    }
}

