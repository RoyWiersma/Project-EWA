import {Component, OnInit} from '@angular/core';
import {DashboardService} from '../../services/dashboard.service';
import {LoginService} from '../../services/login.service';
import {User} from '../../models/User';
import {GP} from '../../models/GP';
import {Patient} from '../../models/Patient';
import {AgendaItem} from '../../models/AgendaItem';
import {isAfter} from 'date-fns';
import {Router} from '@angular/router';
import {Chat} from '../../models/Chat';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {
    patients: Patient[];
    patientData: AgendaItem[];
    doctorData: AgendaItem[];
    currentUser: User;
    doctor: GP;
    selectedUser: Patient;
    loggedInGp: GP;
    date = Date.now();
    chats: Chat[];

    constructor(public dashboardService: DashboardService, public loginService: LoginService, private router: Router) {

    }

    isGP(): boolean {
        return this.currentUser instanceof GP;
    }

    isPatient(): boolean {
        return this.currentUser instanceof Patient;
    }

    showInfo(patients): void {
        this.selectedUser = patients;
    }

    redirectToDossier(): void {
        this.router.navigate(['home/dossier']);
        document.getElementById('modal-span').click();
    }

    redirectToAgenda(): void {
        this.router.navigate(['home/agenda']);
    }

    redirectToChat(): void {

        this.router.navigate(['home/chat']);
    }

    ngOnInit(): void {
        this.currentUser = this.loginService.getLoggedInUser;

        this.dashboardService.getPatientNames()
            .subscribe(response => {
                this.patients = response;
            });
        if (this.isGP()) {
            this.dashboardService.getPatientAppointment().subscribe((appointment: AgendaItem[]) => {
                this.patientData = appointment.filter(item => isAfter(new Date(item.start), new Date()));
            });
        } else {
            this.dashboardService.getPatientAppointment().subscribe((appointment: AgendaItem[]) => {
                this.doctorData = appointment.filter(item => isAfter(new Date(item.start), new Date()));
            });

            this.dashboardService.getDoctorName().subscribe((dat: GP) => {
                this.doctor = dat;
            });
        }

        this.dashboardService.getChat().subscribe((chat: Chat[]) => {
            this.chats = chat;
        });
    }

}
