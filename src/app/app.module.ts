import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainLayoutComponent} from './layouts/main-layout/main-layout.component';
import {RouterModule} from '@angular/router';
import {ComponentsModule} from './components/components.module';

@NgModule({
    imports: [
        AppRoutingModule,
        ComponentsModule,
        RouterModule
    ],
    declarations: [
        AppComponent,
        MainLayoutComponent,
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {}
