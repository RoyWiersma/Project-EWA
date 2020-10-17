import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MainLayoutComponent} from './layouts/main-layout/main-layout.component';
import {CommonModule} from '@angular/common';

const routes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
    },
    {
        path: '',
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
        RouterModule.forRoot(routes, {
            useHash: true
        })
    ],
    exports: []
})
export class AppRoutingModule {
}
