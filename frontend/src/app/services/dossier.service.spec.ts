import { TestBed } from '@angular/core/testing';
import {DossierService} from './dossier.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {DatePipe} from '@angular/common';

describe('DossierService', () => {
    let service: DossierService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                HttpClientTestingModule,
                RouterTestingModule,
            ],
            providers: [
                DatePipe,
            ]
        });
        service = TestBed.inject(DossierService);
        httpMock = TestBed.inject(HttpTestingController);
    });
    afterEach(() => {
        httpMock.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    fit('should be able to get an task', () => {
        // Fake password
        const fakepassword = 'password';

        // subscribing on postPassword
        service.postPassword(fakepassword).subscribe((password) => {
            // expects task to be equal to fake password
            expect(password).toEqual(fakepassword);
        });

        // declaring request which expects the endpoint
        const req = httpMock.expectOne('http://localhost:8085/dossier/login');

        // expects request method to be a get request
        expect(req.request.method).toBe('POST');

        // flush fakePassword
        req.flush(fakepassword);
    });

    fit('should be able to perform another working GET request with a different method', () => {
        // Fake data
        const fakeData = {
            firstname: 'firstname',
            lastname: 'lastname',
        };

        // subscribing on getPatientDossier method
        service.getPatientDossier().subscribe((data) => {
            // expects data to be equal to fakeData instantiated above
            expect(data).toEqual(fakeData);
        });

        // declaring request which expects the api url endpoint
        const req = httpMock.expectOne('http://localhost:8085/dossier/patient');

        // expects request method to be a get request
        expect(req.request.method).toBe('GET');

        // flush fakeData
        req.flush(fakeData);
    });
});
