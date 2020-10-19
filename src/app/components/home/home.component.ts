import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
declare var $: any;

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    userName: string;
    password: string;

    constructor(private router: Router) {
    }

    ngOnInit(): void {
    }

    tempLogin(): void {// Password: TestPass123
        const{userName, password} = this;
        const tempUser: User = new User(1, 'hulsnoah@gmail.com', 'Noah', 'Huls', null, 1, 1, 'm@qen#20NyTestPass123', 'm@qen#20Ny', true);
        if (userName === tempUser.UserName && tempUser.Password === tempUser.Salt + password){
            $('#exampleModalCenter').modal('hide');
            this.router.navigate(['home']);
        }
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
