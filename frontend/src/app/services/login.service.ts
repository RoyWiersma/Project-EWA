import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Type} from '../models/Type';
import {User} from '../models/User';
import {Patient} from '../models/Patient';
import {GP} from '../models/GP';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class LoginService {

    constructor(private httpClient: HttpClient) {}

    get getLoggedInUser(): User {
        const { type, user } = JSON.parse(sessionStorage.getItem('user'));

        let loggedInUser: User;

        if (type === Type.PATIENT) {
            loggedInUser = new Patient(null,
                user.firstName, user.lastName, user.middleName, user.email, user.phoneNumber, user.mobileNumber
            );
        } else if (type === Type.GP) {
            loggedInUser = new GP( null,
                user.firstName, user.lastName, user.middleName, user.email, user.phoneNumber, user.mobileNumber,
                user.isAdmin, user.speciality
            );
        } else {
            console.error(`Something went horribly wrong, user type does not exist ${user.type}`);
        }

        return loggedInUser;
    }

    public postLoginForm(email: string, password: string): Observable<any>{
        return this.httpClient.post<any>(`${environment.apiUrl}/login`, JSON.stringify({email, password}), {
            observe: 'response' as 'body',
            headers: { 'content-type': 'application/json' }
        });
    }

    public getLoginData(token: string, email: string): Promise<any> {
        return this.httpClient.get(`${environment.apiUrl}/user/email?email=${email}`, {
            headers: { Authorization: token }
        }).toPromise();
    }

}
