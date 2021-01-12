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
    error = false;

    constructor(private router: Router, private loginService: LoginService) {

    }

    ngOnInit(): void {
    }

    submitLoginForm(): void {
        const { email, password } = this;
        this.loginService.postLoginForm(email, password)
            .subscribe(async (response: HttpResponse<any>) => {
                if (response !== null) {
                    try {
                        const token = response.headers.get('authorization');
                        const userData = await this.loginService.getLoginData(token, email);

                        sessionStorage.setItem('user', JSON.stringify(userData));
                        localStorage.setItem('jwt', token);

                        $('#exampleModalCenter').modal('hide');

                        this.router.navigate(['home']);
                    } catch (e) {
                        this.error = true;
                    }
                } else {
                    this.error = true;
                }
            }, error => {
                this.error = true;
            });
    }
}
