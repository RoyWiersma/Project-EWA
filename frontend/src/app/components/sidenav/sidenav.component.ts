import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/login.service';
import {GP} from '../../models/GP';
import {Patient} from '../../models/Patient';
import {User} from '../../models/User';
import {Router} from '@angular/router';

@Component({
    selector: 'app-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
    public loggedInGp: GP = null;
    public loggedInPatient: Patient = null;
    public currentUser: User;

    constructor(public loginService: LoginService, private router: Router) {
    }

    ngOnInit(): void {
        if (this.loginService.getLoggedInUser instanceof GP) {
            this.loggedInGp = this.loginService.getLoggedInUser as GP;
        }
        if (this.loginService.getLoggedInUser instanceof Patient) {
            this.loggedInPatient = this.loginService.getLoggedInUser as Patient;
        }
    }

    handleLogout(): void {
        sessionStorage.removeItem('user');

        this.router.navigate(['/']);
    }
}
