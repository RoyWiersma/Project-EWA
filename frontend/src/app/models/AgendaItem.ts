import {Patient} from './Patient';

export class AgendaItem {
    id: number;
    start: Date;
    end: Date;
    onLocation: boolean;
    doctor: object;
    patient: Patient;
    title: string;
    description: string;

    constructor(id: number = null, start: Date = null, end: Date = null, onLocation: boolean = null, doctor: object = null,
                patient: Patient = null, title: string = null, description: string = null) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.onLocation = onLocation;
        this.doctor = doctor;
        this.patient = patient;
        this.title = title;
        this.description = description;
    }
}
