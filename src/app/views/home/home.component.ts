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
                localStorage.setItem('jwt', response.headers.get('authorization'));
                $('#exampleModalCenter').modal('hide');
                this.router.navigate(['home']);
            }, error => {
                console.log(error);
            });
    }
}

class User {
    Id: number;
    UserName: string;
    FirstName: string;
    LastName: string;
    MiddleName: string;
    ContactId: number;
    GPCId: number;
    Password: string;
    Salt: string;
    IsGP: boolean;

    constructor(Id: number,
                UserName: string,
                FirstName: string,
                LastName: string,
                MiddleName: string,
                ContactId: number,
                GPCId: number,
                Password: string,
                Salt: string,
                IsGP: boolean
    ) {
        this.Id = Id;
        this.UserName = UserName;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.MiddleName = MiddleName;
        this.ContactId = ContactId;
        this.GPCId = GPCId;
        this.Password = Password;
        this.Salt = Salt;
        this.IsGP = IsGP;
    }
}
