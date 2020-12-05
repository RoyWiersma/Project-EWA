import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Type} from "../models/Type";
import {Patient} from "../models/Patient";
import {GP} from "../models/GP";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor() { }

    // public getAllAdminGp()
    //
    // public getNoAdminGp()

    public getAllGp(email: string, password: string): Observable<any>{
        return this.httpClient.post<any>(`${this.API_URL}/login`, JSON.stringify({email, password}), {
            observe: 'response' as 'body',
            headers: { 'content-type': 'application/json' }
        }).pipe(map(response => {
            this.httpClient.get(`${this.API_URL}/user/email?email=${email}`, {
                headers: { Authorization: response.headers.get('authorization') }
            }).subscribe((userResp: any) => {
                const { type, user } = userResp;

                if (type === Type.PATIENT) {
                    this.loggedInUser = new Patient(
                        user.firstName, user.lastName, user.middleName, user.email, user.phoneNumber, user.mobileNumber
                    );
                } else if (type === Type.GP) {
                    this.loggedInUser = new GP(
                        user.firstName, user.lastName, user.middleName, user.email, user.phoneNumber, user.mobileNumber,
                        user.isAdmin, user.speciality
                    );
                } else {
                    console.error(`Something went horribly wrong, user type does not exist ${user.type}`);
                }

                console.log(this.loggedInUser);
            });

            return response;
        }));
    }
}
