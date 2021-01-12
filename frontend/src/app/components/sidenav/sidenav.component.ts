import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/login.service';
import {GP} from '../../models/GP';
import {Router} from '@angular/router';

@Component({
    selector: 'app-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit{
    public loggedInGp: GP;

    constructor(public loginService: LoginService, private router: Router) {
    }

    ngOnInit(): void {
        if (this.loginService.getLoggedInUser instanceof GP) {
            this.loggedInGp = this.loginService.getLoggedInUser as GP;
        } else {
            this.loggedInGp = null;
        }
    }

    handleLogout(): void {
        sessionStorage.removeItem('user');

        this.router.navigate(['/']);
    }
}
