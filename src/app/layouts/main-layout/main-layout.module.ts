import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MainLayoutRoutes} from './main-layout.routing';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from '../../views/dashboard/dashboard.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(MainLayoutRoutes)
    ],
    declarations: [
        DashboardComponent
    ]
})
export class MainLayoutModule {}
