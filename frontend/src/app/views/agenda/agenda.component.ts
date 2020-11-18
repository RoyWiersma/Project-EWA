import {Component, OnInit} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {CalendarEvent, CalendarView} from 'angular-calendar';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AgendaService} from '../../services/agenda.service';
import {AgendaItem} from '../../models/agendaItem';
import {PatientService} from '../../services/patient.service';
import {map} from 'rxjs/operators';

const colors: any = {
    red: {
        primary: '#ad2121',
        secondary: '#FAE3E3',
    },
    blue: {
        primary: '#1e90ff',
        secondary: '#D1E8FF',
    },
};

export class ValidationResponse {
    success = false;
    message = null;
    errors = { title: '', start: '', end: '' };
}

@Component({
    selector: 'app-agenda',
    templateUrl: './agenda.component.html',
    styleUrls: ['./agenda.component.css']
})
export class AgendaComponent implements OnInit {

    view: CalendarView = CalendarView.Month;

    CalendarView = CalendarView;
    viewDate: Date = new Date();

    refresh: Subject<any> = new Subject();
    events$: Observable<CalendarEvent<{ item: AgendaItem }>[]>;
    activeDayIsOpen = true;
    addModalOpen = false;

    appointmentForm = new AgendaItem();
    response: ValidationResponse;

    constructor(private modal: NgbModal, private agendaService: AgendaService, public patientService: PatientService) {
        this.response = new ValidationResponse();
    }

    ngOnInit(): void {
        this.fetchAgendaItems();
    }

    submitAgendaForm(): void {
        this.agendaService.saveAgendaItem(this.appointmentForm)
            .subscribe(resp => {
                this.response.success = true;
                this.response.message = resp.message;

                this.closeModel();
                this.fetchAgendaItems();
            }, error => {
                this.response.success = false;
                this.response.message = error.error.message || '';
                this.response.errors = error.error.errors || {};
            });
    }

    handleOnEventClick(event: CalendarEvent): void {
        Object.keys(event).forEach(key => this.appointmentForm[key] = event[key]);
        this.addModalOpen = true;
    }

    openModel(date: Date): void {
        this.addModalOpen = !this.addModalOpen;
        this.appointmentForm.start = date;
    }

    deleteEvent(): void {
        this.agendaService.deleteAgendaItem(this.appointmentForm.id)
            .subscribe(resp => {
                this.response.success = true;
                this.response.message = resp.message;

                this.closeModel();
                this.fetchAgendaItems();
            }, error => {
                this.response.success = false;
                this.response.message = 'Er is wat fout gegaan tijdens het verwijderen van de afspraak';
            });
    }

    setView(view: CalendarView): void {
        this.view = view;
    }

    closeOpenMonthViewDay(): void {
        this.activeDayIsOpen = false;
    }

    closeModel(): void {
        this.addModalOpen = false;
        this.response = new ValidationResponse();
        this.appointmentForm = new AgendaItem();
    }

    private fetchAgendaItems(): void {
        this.events$ = this.agendaService.getAgendaItems()
            .pipe(
                map((results: AgendaItem[]) => {
                    return results.map((item: AgendaItem) => {
                        const {id, title, start, end, onLocation, patient, doctor, description} = item;
                        const color =  onLocation ? colors.red : colors.blue;

                        return { id, title, end: new Date(end), start: new Date(start), color, patient, doctor, onLocation, description };
                    });
                })
            );
    }
}
