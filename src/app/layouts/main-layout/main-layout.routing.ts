import {Routes} from '@angular/router';
import {DashboardComponent} from '../../views/dashboard/dashboard.component';
import {AgendaComponent} from '../../components/agenda/agenda.component';

export const MainLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'agenda', component: AgendaComponent },
];
