import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {GpDetailsComponent} from './gp-details.component';
import {GpService} from '../../../services/gp.service';
import {GpMockService} from '../../../mocks/gp-mock.service';
import objectContaining = jasmine.objectContaining;
import {ActivatedRoute, RouterModule} from '@angular/router';
import {of} from 'rxjs';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

describe('GpDetailsComponent', () => {
    let component: GpDetailsComponent;
    let fixture: ComponentFixture<GpDetailsComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [GpDetailsComponent],
            imports: [
                RouterModule,
                FormsModule,
                HttpClientModule,
            ],
            providers: [
                {
                    provide: GpService,
                    useClass: GpMockService,
                },
                {
                    provide: ActivatedRoute,
                    useValue: {
                        params: of({ id: 1 })
                    }
                }
            ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(GpDetailsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should load correct patient detail', () => {
        expect(component.gp).toEqual(objectContaining({
            firstName: 'Dokter 1',
            lastName: 'test',
            middleName: 'van',
            email: 'Dokter1@test.nl',
            phoneNumber: '0123456',
            mobileNumber: '0123456',
            password: null,
            gp: null,
            isAdmin: null,
            speciality: null,
            id: 1

        }));
    });
});
