import {Component, OnInit} from '@angular/core';
import {Patient} from '../../models/Patient';
import {DoctorService} from '../../services/doctor.service';
import {ValidationResponse} from '../agenda/agenda.component';

@Component({
    selector: 'app-patients',
    templateUrl: './patients.component.html',
    styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {

    public readonly ROWS_PER_PAGE = 10;

    patients: Patient[];
    currentPage = 0;
    maxPages = 0;
    response: ValidationResponse;

    newPatient = new Patient();
    addPatientModalOpen = false;

    constructor(private doctorService: DoctorService) {
        this.response = new ValidationResponse();
    }

    ngOnInit(): void {
        this.patients = [];
        this.doctorService.getPatients()
            .subscribe(response => {
                this.patients = response.patients;
                this.maxPages = Math.ceil(this.patients.length / this.ROWS_PER_PAGE);
            });
    }

    addPatient(): void {
        this.doctorService.addPatient(this.newPatient)
            .subscribe(response => {
                this.response.success = true;
                this.response.message = 'Patient is toegevoegd';
                this.addPatientModalOpen = false;
                this.ngOnInit();
            }, error => {
                this.response.success = false;
                this.response.message = 'Er is wat fout gegaan tijdens opslaan van de patient';
            });
    }

    deletePatient(id: number): void {
       if (confirm(`Wil je patient met id ${id} verwijderen?`)) {
           this.doctorService.deletePatient(id)
               .subscribe(response => {
                   this.response.success = true;
                   this.response.message = `Patient met id '${id}' is verwijderd`;
                   this.ngOnInit();
               }, error => {
                   this.response.success = false;
                   this.response.message = `Er is wat fout gegaan tijdens het verwijderen van patient ${id}`;
               });
       }
    }

    setPage(page: number): void {
        this.currentPage = page;
    }

    toArray(n: number): number[] {
        return [...Array(n).keys()];
    }

    openModal(): void {
        this.addPatientModalOpen = true;
        this.newPatient = new Patient();
    }

    closeModal(): void {
        this.addPatientModalOpen = false;
    }
}
