import {TestBed} from '@angular/core/testing';

import {DashboardService} from './dashboard.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";
import {DatePipe} from "@angular/common";

describe('DashboardService', () => {
    let service: DashboardService;
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
        service = TestBed.inject(DashboardService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    fit('should be able to perform a working GET request', () => {
        // Fake data
        const fakeData = {
            firstname: 'firstname',
            lastname: 'lastname',
        };

        // subscribing on getDoctorname method
        service.getDoctorName().subscribe((data) => {
            // expects data to be equal to fakeData instantiated above
            expect(data).toEqual(fakeData);
        });

        // declaring request which expects the api url endpoint
        const req = httpMock.expectOne('http://localhost:8085/dashboard/doctor');

        // expects request method to be a get request
        expect(req.request.method).toBe('GET');

        // flush dummyTask
        req.flush(fakeData);
    });


});

