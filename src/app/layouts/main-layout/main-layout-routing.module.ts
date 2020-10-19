import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from '../../views/dashboard/dashboard.component';
import {AgendaComponent} from '../../views/agenda/agenda.component';
import {NgModule} from '@angular/core';

const routes: Routes = [
    { path: '', component: DashboardComponent },
    { path: 'agenda', component: AgendaComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MainLayoutRoutingModule {}
