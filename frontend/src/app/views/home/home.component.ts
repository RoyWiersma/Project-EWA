import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../services/login.service';
import {HttpResponse} from '@angular/common/http';
declare var $: any;

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    email: string;
    password: string;

    constructor(private router: Router, private loginService: LoginService) {
    }

    ngOnInit(): void {
    }

    submitLoginForm(): void {
        const { email, password } = this;
        this.loginService.postLoginForm(email, password)
            .subscribe((response: HttpResponse<any>) => {
                if (response !== null) {
                    localStorage.setItem('jwt', response.headers.get('authorization'));
                    $('#exampleModalCenter').modal('hide');
                    this.router.navigate(['home']);
                } else {
                    // TODO: geef error aan login form
                }
            }, error => {
                console.log(error);
            });
    }
}
