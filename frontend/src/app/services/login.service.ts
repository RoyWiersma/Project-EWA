import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Type} from '../models/Type';
import {User} from '../models/User';
import {Patient} from '../models/Patient';
import {GP} from '../models/GP';

@Injectable({
    providedIn: 'root'
})
export class LoginService {

    private readonly API_URL = 'http://localhost:8085';

    constructor(private httpClient: HttpClient) {}

    get getLoggedInUser(): User {
        const { type, user } = JSON.parse(sessionStorage.getItem('user'));
        let loggedInUser: User;

        if (type === Type.PATIENT) {
            loggedInUser = new Patient(
                user.firstName, user.lastName, user.middleName, user.email, user.phoneNumber, user.mobileNumber
            );
        } else if (type === Type.GP) {
            loggedInUser = new GP(
                user.firstName, user.lastName, user.middleName, user.email, user.phoneNumber, user.mobileNumber,
                user.isAdmin, user.speciality
            );
        } else {
            console.error(`Something went horribly wrong, user type does not exist ${user.type}`);
        }

        return loggedInUser;
    }

    public postLoginForm(email: string, password: string): Observable<any>{
        return this.httpClient.post<any>(`${this.API_URL}/login`, JSON.stringify({email, password}), {
            observe: 'response' as 'body',
            headers: { 'content-type': 'application/json' }
        });
    }

    public getLoginData(token: string, email: string): Promise<any> {
        return this.httpClient.get(`${this.API_URL}/user/email?email=${email}`, {
            headers: { Authorization: token }
        }).toPromise();
    }

}
