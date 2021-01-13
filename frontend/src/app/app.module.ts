import {LOCALE_ID, NgModule} from '@angular/core';

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
import {CountdownModule} from 'ngx-countdown';
import '@angular/common/locales/global/nl';
import { GpsComponent } from './views/gps/gps.component';
import { GpDetailsComponent } from './views/gps/gp-details/gp-details.component';

@NgModule({
    imports: [
        AppRoutingModule,
        ComponentsModule,
        BrowserModule,
        RouterModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpClientModule,
        CountdownModule
    ],
    declarations: [
        AppComponent,
        MainLayoutComponent,
        HomeComponent
    ],
    providers: [ {provide: LOCALE_ID, useValue: 'nl'}],
    bootstrap: [AppComponent]
})
export class AppModule {}
