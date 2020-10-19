import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SidenavComponent} from './sidenav/sidenav.component';
import { HomeComponent } from './home/home.component';
import {RouterModule} from '@angular/router';

@NgModule({
    declarations: [
        SidenavComponent,
        HomeComponent
    ],
    exports: [
        SidenavComponent,
        HomeComponent
    ],
    imports: [
        CommonModule,
        RouterModule
    ]
})
export class ComponentsModule {}
