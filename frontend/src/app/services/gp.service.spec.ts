import {TestBed} from '@angular/core/testing';

import {GpService} from './gp.service';
import {HttpClientModule} from '@angular/common/http';
import {GP} from '../models/GP';

describe('GpService', () => {
    let service: jasmine.SpyObj<GpService>;

    beforeEach(() => {
        const spy = jasmine.createSpyObj('GpService', ['addGP', 'updateGP']);

        TestBed.configureTestingModule({
            imports: [HttpClientModule],
            providers: [
                {provide: GpService, useValue: spy}
            ]
        });

        service = TestBed.inject(GpService) as jasmine.SpyObj<GpService>;

    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should call addGP ', () => {
        const testGp = new GP(1, 'test', 'test', 'test', 'test');
        service.addGP(testGp);

        expect(service.addGP).toHaveBeenCalledWith(testGp);
    });

    it('should call updateGP() and return observable', () => {
        const testGp = new GP(1, 'test', 'test', 'test', 'test');
        service.updateGP(testGp, 1 );

        expect(service.updateGP).toHaveBeenCalledWith(testGp, 1);
    });

});
