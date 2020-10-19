import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MainLayoutComponent} from './layouts/main-layout/main-layout.component';
import {CommonModule} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {HomeComponent} from './components/home/home.component';

const routes: Routes = [
    {
        path: '',
        component: HomeComponent
    },
    {
        path: 'home',
        component: MainLayoutComponent,
        children: [{
            path: '',
            loadChildren: () => import('./layouts/main-layout/main-layout.module').then(m => m.MainLayoutModule)
        }]
    }
];

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        RouterModule.forRoot(routes, {
            useHash: true
        })
    ],
    exports: []
})
export class AppRoutingModule {
}
