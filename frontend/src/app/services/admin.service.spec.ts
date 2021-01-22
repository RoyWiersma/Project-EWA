import {inject, TestBed} from '@angular/core/testing';

import { AdminService } from './admin.service';
import {HttpClientModule} from '@angular/common/http';
import {GP} from '../models/GP';

describe('AdminService', () => {
    let service: jasmine.SpyObj<AdminService>;

    beforeEach(() => {
      const spy = jasmine.createSpyObj('AdminService', ['getAllGp', 'setAdmin', 'setNoAdmin']);

      TestBed.configureTestingModule({
        imports: [HttpClientModule],
        providers: [
            { provide: AdminService, useValue: spy }
        ]
      });

      service = TestBed.inject(AdminService) as jasmine.SpyObj<AdminService>;
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should call getAllGp() and return observable', () => {
        service.getAllGp();
        expect(service.getAllGp).toHaveBeenCalled();
    });

    it('should call setAdmin() and return observable', () => {
        const admin = new GP(1, 'test', 'test', 'test', 'test',
            '123123', null, true);
        service.setAdmin(admin);

        expect(service.setAdmin).toHaveBeenCalledWith(admin);
    });

    it('should call setNoAdmin() and return observable', () => {
        const gp = new GP(1, 'test', 'test', 'test', 'test',
            '123123', null, false);
        service.setNoAdmin(gp);

        expect(service.setNoAdmin).toHaveBeenCalledWith(gp);
    });
});
