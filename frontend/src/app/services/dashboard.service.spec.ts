import { TestBed } from '@angular/core/testing';

import {DashboardService} from './dashboard.service';

describe('DashboardService', () => {
    let service: DashboardService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(DashboardService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});

// describe('ValueService', () => {
//     let service: DashboardService;
//     beforeEach(() => { service = new DashboardService(); });
//
//     it('#getValue should return real value', () => {
//         expect(service.getDoctor()).toBe('real value');
//     });
//
//     it('#getObservableValue should return value from observable',
//         (done: DoneFn) => {
//             service.getDoctorName().subscribe(value => {
//                 expect(value).toBe('observable value');
//                 done();
//             });
//         });
//
// });
