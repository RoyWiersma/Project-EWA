import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {AgendaComponent} from './agenda.component';
import {HttpClientModule} from '@angular/common/http';
import {RouterTestingModule} from '@angular/router/testing';
import {AgendaService} from '../../services/agenda.service';
import {AgendaMockService} from '../../mocks/agenda-mock.service';
import {LoginService} from '../../services/login.service';
import {CalendarModule, DateAdapter} from 'angular-calendar';
import {adapterFactory} from 'angular-calendar/date-adapters/date-fns';
import {PatientService} from '../../services/patient.service';
import {PatientMockService} from '../../mocks/patient-mock.service';
import {DebugElement} from '@angular/core';

describe('AgendaComponent', () => {
    let component: AgendaComponent;
    let fixture: ComponentFixture<AgendaComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AgendaComponent],
            imports: [
                HttpClientModule,
                RouterTestingModule,
                CalendarModule.forRoot({
                    provide: DateAdapter,
                    useFactory: adapterFactory,
                }),
            ],
            providers: [
                {
                    provide: AgendaService,
                    useClass: AgendaMockService,

                },
                {
                    provide: PatientService,
                    useClass: PatientMockService,

                }
            ]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(AgendaComponent);
            component = fixture.componentInstance;
            fixture.detectChanges();

        });
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should render previous button', () => {
        const dbElement: DebugElement = fixture.debugElement;
        const tableRows: HTMLTableRowElement[] = dbElement.nativeElement.querySelectorAll('#prevButton');

        expect(tableRows.length).toEqual(1);
    });

});
