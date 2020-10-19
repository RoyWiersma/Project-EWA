import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainLayoutComponent} from './layouts/main-layout/main-layout.component';
import {RouterModule} from '@angular/router';
import {ComponentsModule} from './components/components.module';
import {FormsModule} from '@angular/forms';

@NgModule({
    imports: [
        AppRoutingModule,
        ComponentsModule,
        RouterModule,
        FormsModule
    ],
    declarations: [
        AppComponent,
        MainLayoutComponent,
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {}
