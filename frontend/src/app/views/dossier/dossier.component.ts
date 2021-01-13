import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/login.service';
import {User} from '../../models/User';
import {GP} from '../../models/GP';
import {Patient} from '../../models/Patient';
import {Router} from '@angular/router';
import {DossierService} from '../../services/dossier.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
declare var $: any;

@Component({
    selector: 'app-dashboard',
    templateUrl: './dossier.component.html',
    styleUrls: ['./dossier.component.css']
})

export class DossierComponent implements OnInit {
    public currentUser: User;
    public counter: number;
    public afkCounter: number;
    public patientDossier: Patient;
    public password: string;

    constructor(public loginservice: LoginService, private router: Router, public dossierService: DossierService) {

    }

    startCountdown(seconds): void {
        this.counter = seconds;
        let reset = 10;
        this.afkCounter = 60;

        const interval = setInterval(() => {
            this.counter--;
            if (this.counter === 0) {
                document.getElementById('countdownModalButton').click();
                const afkInterval = setInterval(() => {
                    document.getElementById('modalButton').addEventListener('click', () => {
                        clearInterval(afkInterval);
                        this.counter = reset;
                        if (reset === 0){
                            document.getElementById('countdownModalButton').click();
                            return;
                        }
                    });
                    this.afkCounter--;
                    if (this.afkCounter === 0) {
                        clearInterval(afkInterval);
                        clearInterval(interval);
                        this.router.navigate(['home']);
                        document.getElementById('modalButton').click();
                    }
                }, 1000);
            }
        }, 1000);
    }

    back(): void{
        history.back();
        $('#loginModal').modal('hide');
    }

    enterPassword(): void {
        this.dossierService.postPassword(this.password)
            .subscribe((response: HttpResponse<any>) => {
                if (response != null) {
                    document.getElementById('loginModalButton').click();
                    this.startCountdown(10);
                    this.dossierService.getPatientDossier().subscribe((result: Patient) => {
                        this.patientDossier = result[0];
                    });
                }
                else {
                    console.error('er ging iets mis');
                }
            }, error => {
                if (error instanceof HttpErrorResponse){
                    document.getElementById('hiddenValidator').hidden = false;
                    document.getElementById('wrongPassword').innerHTML = 'Verkeerd wachtwoord!';
                }
            });
    }

    isGP(): boolean {
        return this.currentUser instanceof GP;
    }

    isPatient(): boolean {
        return this.currentUser instanceof Patient;
    }

    ngOnInit(): void {
        this.currentUser = this.loginservice.getLoggedInUser;
        if (this.isPatient()) {
            document.getElementById('loginModalButton').click();

        }
    }

}
