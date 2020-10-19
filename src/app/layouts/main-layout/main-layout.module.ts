import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MainLayoutRoutes} from './main-layout.routing';
import {CommonModule} from '@angular/common';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    imports: [
        CommonModule,
        NgbModalModule,
        RouterModule.forChild(MainLayoutRoutes),
        CalendarModule.forRoot({
          provide: DateAdapter,
          useFactory: adapterFactory,
        }),
    ],
    declarations: []
})
export class MainLayoutModule {}
