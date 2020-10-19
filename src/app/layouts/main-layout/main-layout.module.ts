import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import {AgendaComponent} from '../../views/agenda/agenda.component';
import {DashboardComponent} from '../../views/dashboard/dashboard.component';
import {FormsModule} from '@angular/forms';
import {MainLayoutRoutingModule} from './main-layout-routing.module';

@NgModule({
    imports: [
        CommonModule,
        NgbModalModule,
        FormsModule,
        MainLayoutRoutingModule,
        CalendarModule.forRoot({
          provide: DateAdapter,
          useFactory: adapterFactory,
        }),
    ],
    declarations: [
        AgendaComponent,
        DashboardComponent
    ]
})
export class MainLayoutModule {}
