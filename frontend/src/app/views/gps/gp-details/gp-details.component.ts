import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {ValidationResponse} from '../../agenda/agenda.component';
import {DossierService} from '../../../services/dossier.service';
import {GP} from '../../../models/GP';
import {GpService} from '../../../services/gp.service';

@Component({
  selector: 'app-gp-details',
  templateUrl: './gp-details.component.html',
  styleUrls: ['./gp-details.component.css']
})
export class GpDetailsComponent implements OnInit {

    gp: GP;
    GPId: number;
    editedGP: GP;

    response: ValidationResponse;

    constructor(private activeRoute: ActivatedRoute, private dossierService: DossierService,
                private gpService: GpService) {
        this.response = new ValidationResponse();
    }

    ngOnInit(): void {
        this.activeRoute.params.subscribe((params: Params) => {
            this.GPId = Number.parseInt(params.id);

            this.gpService.getGP(this.GPId)
                .subscribe(response => {
                    this.gp = Object.assign(new GP(), response.gp);
                    this.editedGP = Object.assign(new GP(), response.gp);
                }, error => {
                    this.response.message = 'Er is een fout ontstaan tijdens het ophalen van de gegevens van de dokter';
                    this.response.success = false;
                });
        });
    }

    updateGP(): void {
        this.response.message = null;
        this.gpService.updateGP(this.editedGP, this.GPId)
            .subscribe(response => {
                this.gp = Object.assign(new GP(), response.gp);
                this.editedGP = Object.assign(new GP(), response.gp);

                this.response.message = 'Dokter geupdate';
                this.response.success = true;
            }, error => {
                this.response.message = 'Er is een fout ontstaan tijdens het updaten van de dokter';
                this.response.success = false;
            });
    }

    isEdited(original: GP, edited: GP): boolean {
        return JSON.stringify(original) !== JSON.stringify(edited);
    }
}
