import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CalendarModule, DateAdapter} from 'angular-calendar';
import {adapterFactory} from 'angular-calendar/date-adapters/date-fns';
import {NgbModalModule, NgbTooltipModule} from '@ng-bootstrap/ng-bootstrap';
import {AgendaComponent} from '../../views/agenda/agenda.component';
import {DashboardComponent} from '../../views/dashboard/dashboard.component';
import {FormsModule} from '@angular/forms';
import {MainLayoutRoutingModule} from './main-layout-routing.module';
import {OWL_DATE_TIME_LOCALE, OwlDateTimeModule, OwlNativeDateTimeModule} from 'ng-pick-datetime';
import {CountdownModule} from 'ngx-countdown';
import {DossierComponent} from '../../views/dossier/dossier.component';
import {ChatComponent} from '../../views/chat/chat.component';
import {AdminComponent} from '../../views/admin/admin.component';
import { PatientsComponent } from '../../views/patients/patients.component';
import {PatientDetailsComponent} from '../../views/patients/patient-details/patient-details.component';
import {GpDetailsComponent} from '../../views/gps/gp-details/gp-details.component';
import {GpsComponent} from '../../views/gps/gps.component';

@NgModule({
    imports: [
        CommonModule,
        NgbModalModule,
        FormsModule,
        OwlDateTimeModule,
        OwlNativeDateTimeModule,
        MainLayoutRoutingModule,
        CalendarModule.forRoot({
            provide: DateAdapter,
            useFactory: adapterFactory,
        }),
        CountdownModule,
        NgbTooltipModule,
    ],
    declarations: [
        AgendaComponent,
        DashboardComponent,
        DossierComponent,
        ChatComponent,
        AdminComponent,
        PatientsComponent,
        PatientDetailsComponent,
        GpsComponent,
        GpDetailsComponent
    ],
    providers: [
        { provide: OWL_DATE_TIME_LOCALE, useValue: 'nl' }
    ]
})

export class MainLayoutModule {}
