import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SidenavComponent} from './sidenav/sidenav.component';
import { HomeComponent } from './home/home.component';
import {FormsModule} from "@angular/forms";

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
        FormsModule
    ]
})
export class ComponentsModule {}
