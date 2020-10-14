import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MainLayoutRoutes} from './main-layout.routing';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from '../../views/dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import {AgendaComponent} from '../../components/agenda/agenda.component';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(MainLayoutRoutes),
        BrowserAnimationsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    
    AgendaComponent,
    ],
    declarations: [
        DashboardComponent
    ]
})
export class MainLayoutModule {}
