import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class LoginService {

    constructor(private httpClient: HttpClient) {}

    public postLoginForm(email: string, password: string): Observable<any>{
        return this.httpClient.post<any>('http://localhost:8085/login', JSON.stringify({email, password}), {
            observe: 'response' as 'body',
            headers: { 'content-type': 'application/json' }
        }).pipe(map(user => {
            return user;
        }));
    }

}
