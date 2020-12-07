import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/login.service';
import {GP} from '../../models/GP';

@Component({
    selector: 'app-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit{
    public loggedInGp: GP;

    constructor(public loginService: LoginService) {
    }

    ngOnInit(): void {
        if (this.loginService.getLoggedInUser instanceof GP){
            this.loggedInGp = this.loginService.getLoggedInUser as GP;
        }
    }
}
