import { TestBed } from '@angular/core/testing';

import { AgendaService } from './agenda.service';
import {HttpClientModule} from '@angular/common/http';
import {AgendaItem} from '../models/AgendaItem';

describe('AgendaService', () => {
    let service: jasmine.SpyObj<AgendaService>;
    beforeEach(() => {
        const spy = jasmine.createSpyObj('AgendaService', ['deleteAgendaItem', 'saveAgendaItem', 'getAgendaItems', 'postRequest']);

        TestBed.configureTestingModule({
            imports: [HttpClientModule],
            providers: [
                { provide: AgendaService, useValue: spy }
            ]
        });

        service = TestBed.inject(AgendaService) as jasmine.SpyObj<AgendaService>;
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should call getAgendaItems()', () => {
        const agendaItem = new AgendaItem(1);
        service.deleteAgendaItem(1);
        expect(service.deleteAgendaItem).toHaveBeenCalledWith(1);
    });

    it('should call saveAgendaItems()', () => {
        const agendaItem = new AgendaItem();
        service.saveAgendaItem(agendaItem);
        expect(service.saveAgendaItem).toHaveBeenCalledWith(agendaItem);
    });

    it('should call getAgendaItems()', () => {
        service.getAgendaItems();
        expect(service.getAgendaItems).toHaveBeenCalled();
    });

    it('should call postRequest()', () => {
        const agendaItem = new AgendaItem();
        service.postRequest(agendaItem);
        expect(service.postRequest).toHaveBeenCalledWith(agendaItem);
    });
});
