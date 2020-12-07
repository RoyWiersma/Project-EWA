import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from '../../views/dashboard/dashboard.component';
import {AgendaComponent} from '../../views/agenda/agenda.component';
import {ChatComponent} from '../../views/chat/chat.component';
import {NgModule} from '@angular/core';
import {AdminComponent} from '../../views/admin/admin.component';

const routes: Routes = [
    { path: '', component: DashboardComponent },
    { path: 'agenda', component: AgendaComponent },
    { path: 'chat', component: ChatComponent},
    { path: 'admin', component: AdminComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MainLayoutRoutingModule {}
