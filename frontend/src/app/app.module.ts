import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainLayoutComponent} from './layouts/main-layout/main-layout.component';
import {ComponentsModule} from './components/components.module';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule} from '@angular/forms';
import {HomeComponent} from './views/home/home.component';
import {RouterModule} from '@angular/router';
import { HttpClientModule} from '@angular/common/http';
import { AdminComponent } from './views/admin/admin.component';

@NgModule({
    imports: [
        AppRoutingModule,
        ComponentsModule,
        BrowserModule,
        RouterModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpClientModule
    ],
    declarations: [
        AppComponent,
        MainLayoutComponent,
        HomeComponent,
        AdminComponent,
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {}
