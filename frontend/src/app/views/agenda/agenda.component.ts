import {Component, OnInit} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {CalendarEvent, CalendarView} from 'angular-calendar';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AgendaService} from '../../services/agenda.service';
import {AgendaItem} from '../../models/AgendaItem';
import {PatientService} from '../../services/patient.service';
import {LoginService} from '../../services/login.service';
import {map} from 'rxjs/operators';
import {GP} from '../../models/GP';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {DoctorService} from '../../services/doctor.service';
import {Patient} from '../../models/Patient';
import {environment} from '../../../environments/environment';

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
    patients: Patient[];
    events$: Observable<CalendarEvent<{ item: AgendaItem }>[]>;
    activeDayIsOpen = true;
    addModalOpen = false;

    appointmentForm = new AgendaItem();
    response: ValidationResponse;

    constructor(private modal: NgbModal, private agendaService: AgendaService,
                public patientService: PatientService, private loginService: LoginService, public doctorService: DoctorService,
                private httpClient: HttpClient, private router: Router) {
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
                this.response.message = error.error.message || 'Er is een fout onstaan tijdens het opslaan';
                this.response.errors = error.error.errors || {};
            });
    }

    handleOnEventClick(event: CalendarEvent): void {
        Object.keys(event).forEach(key => this.appointmentForm[key] = event[key]);
        this.addModalOpen = true;
    }

    openModel(date: Date): void {
    if (this.loginService.getLoggedInUser instanceof GP){
        this.addModalOpen = !this.addModalOpen;
        this.appointmentForm.start = date;
        }
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

    createChatRoom(): void {
        const patient = this.appointmentForm.patient;

        this.httpClient.post(`${environment.apiUrl}/chat`, { patient }, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        }).subscribe(() => {
            this.router.navigate([`home/chat`]);
        });
    }

    private fetchAgendaItems(): void {
        this.doctorService.getPatients()
            .subscribe(response => {
                this.patients = response.patients;
            }, error => console.error(error));

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
