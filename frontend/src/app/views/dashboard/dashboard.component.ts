import {Component, OnInit} from '@angular/core';
import {GP} from "../../models/GP";
import {LoginService} from "../../services/login.service";

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {
    public loggedInGp: GP;

    constructor(public loginService: LoginService) {
    }

    ngOnInit(): void {
        if (this.loginService.getLoggedInUser instanceof GP){
            this.loggedInGp = this.loginService.getLoggedInUser as GP;
        }
    }

}
