import {Component, OnInit} from '@angular/core';
import {GP} from '../../models/GP';
import {GpService} from '../../services/gp.service';
import {ValidationResponse} from '../agenda/agenda.component';

@Component({
    selector: 'app-gps',
    templateUrl: './gps.component.html',
    styleUrls: ['./gps.component.css']
})
export class GpsComponent implements OnInit {

    public readonly ROWS_PER_PAGE = 10;

    gps: GP[];
    currentPage = 0;
    maxPages = 0;
    response: ValidationResponse;

    newGP = new GP();
    addGPModalOpen = false;

    constructor(private gpService: GpService) {
        this.response = new ValidationResponse();
    }

    ngOnInit(): void {
        this.gps = [];
        this.gpService.getGPs()
            .subscribe(response => {
                this.gps = response.gps;
                this.maxPages = Math.ceil(this.gps.length / this.ROWS_PER_PAGE);
            });
    }

    addGP(): void {
        this.gpService.addGP(this.newGP)
            .subscribe(response => {
                this.response.success = true;
                this.response.message = 'De dokter is toegevoegd';
                this.addGPModalOpen = false;
                this.ngOnInit();
            }, error => {
                this.response.success = false;
                this.response.message = 'Er is wat fout gegaan tijdens opslaan van de dokter';
            });
    }

    deleteGP(id: number): void {
        if (confirm(`Wil je patient met id ${id} verwijderen?`)) {
            this.gpService.deleteGP(id)
                .subscribe(response => {
                    this.response.success = true;
                    this.response.message = `De dokter met id '${id}' is verwijderd`;
                    this.ngOnInit();
                }, error => {
                    this.response.success = false;
                    this.response.message = `Er is wat fout gegaan tijdens het verwijderen van de dokter ${id}`;
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
        this.addGPModalOpen = true;
        this.newGP = new GP();
    }

    closeModal(): void {
        this.addGPModalOpen = false;
    }
}
