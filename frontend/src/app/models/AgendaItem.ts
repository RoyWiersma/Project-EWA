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
}
