import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {DoctorService} from '../../../services/doctor.service';
import {Patient} from '../../../models/Patient';
import {PatientService} from '../../../services/patient.service';
import {ValidationResponse} from '../../agenda/agenda.component';
import {Dossier} from '../../../models/Dossier';
import {DossierService} from '../../../services/dossier.service';

@Component({
    selector: 'app-patient-details',
    templateUrl: './patient-details.component.html',
    styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit {

    patient: Patient;
    patientId: number;
    editedPatient: Patient;
    dossier: Dossier[];

    dossierModalOpen = false;
    dossierData: Dossier;

    response: ValidationResponse;

    constructor(private activeRoute: ActivatedRoute, private dossierService: DossierService,
                private doctorService: DoctorService, private patientService: PatientService) {
        this.response = new ValidationResponse();
    }

    ngOnInit(): void {
        this.activeRoute.params.subscribe((params: Params) => {
            this.patientId = Number.parseInt(params.id);

            this.doctorService.getPatient(this.patientId)
                .subscribe(response => {
                    this.patient = Object.assign(new Patient(), response.patient);
                    this.editedPatient = Object.assign(new Patient(), response.patient);
                }, error => {
                    this.response.message = 'Er is een fout ontstaan tijdens het ophalen van de gegevens van de patient';
                    this.response.success = false;
                });

            this.fetchDossierItems();
        });
    }

    updatePatient(): void {
        this.response.message = null;
        this.patientService.updatePatient(this.editedPatient, this.patientId)
            .subscribe(response => {
                this.patient = Object.assign(new Patient(), response.patient);
                this.editedPatient = Object.assign(new Patient(), response.patient);

                this.response.message = 'Patient geupdate';
                this.response.success = true;
            }, error => {
                this.response.message = 'Er is een fout ontstaan tijdens het updaten van de patient';
                this.response.success = false;
            });
    }

    isEdited(original: Patient, edited: Patient): boolean {
        return JSON.stringify(original) !== JSON.stringify(edited);
    }

    deleteDossierItem(id: number): void {
        this.response.message = null;
        this.dossierService.deleteItemById(id)
            .subscribe(response => {
                this.response.success = true;
                this.response.message = response.message;

                this.fetchDossierItems();
            }, error => {
                this.response.success = false;
                this.response.message = error.message;
            });
    }

    openDetailModel(id: number): void {
        this.dossierModalOpen = true;
        this.dossierService.getMediaItem(id)
            .subscribe(response => {
                this.dossierData = { ...response.media, image: response.media.media };
            });
    }

    addDossierMediaItem(): void {
        this.dossierModalOpen = true;
        this.dossierData = null;
        this.dossierData = new Dossier();

        console.log(this.dossierData.id === undefined);
    }

    saveMediaItem(): void {
        const formData = new FormData();
        formData.append('image', this.dossierData.image, this.dossierData.image.name);
        formData.append('description', this.dossierData.description);

        this.dossierService.saveMediaItem(formData, this.patientId)
            .subscribe(response => {
                this.dossierModalOpen = false;
                this.response.message = 'Media item is opgeslagen';
                this.response.success = true;

                this.ngOnInit();
            }, error => {
                this.response.message = 'Er is een fout ontstaan tijdens het opslaan van de media item';
                this.response.success = false;
            });
    }

    fileInputChange(event): void {
        this.dossierData.image = event.target.files[0];
    }

    private fetchDossierItems(): void {
        this.dossier = [];
        this.dossierService.getAllByPatient(this.patientId)
            .subscribe(response => {
                response.dossier.media.forEach(item => {
                    this.dossier.push({ ...item, type: 'media' });
                });
            }, error => {
                this.response.message = 'Er is een fout ontstaan tijdens het ophalen van de gegevens van de patient';
                this.response.success = false;
            });
    }
}
