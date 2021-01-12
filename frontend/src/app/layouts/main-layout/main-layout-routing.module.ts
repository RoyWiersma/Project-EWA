import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from '../../views/dashboard/dashboard.component';
import {DossierComponent} from '../../views/dossier/dossier.component';
import {AgendaComponent} from '../../views/agenda/agenda.component';
import {ChatComponent} from '../../views/chat/chat.component';
import {NgModule} from '@angular/core';
import {AdminComponent} from '../../views/admin/admin.component';
import {PatientsComponent} from '../../views/patients/patients.component';
import {PatientDetailsComponent} from '../../views/patients/patient-details/patient-details.component';
import {GpsComponent} from '../../views/gps/gps.component';
import {GpDetailsComponent} from '../../views/gps/gp-details/gp-details.component';

const routes: Routes = [
    { path: '', component: DashboardComponent },
    { path: 'agenda', component: AgendaComponent },
    { path: 'dossier', component: DossierComponent },
    { path: 'chat', component: ChatComponent},
    { path: 'admin', component: AdminComponent },
    {
        path: 'gps',
        children: [
            { path: '', component: GpsComponent },
            { path: ':id', component: GpDetailsComponent }
        ]
    },
    {
        path: 'patients',
        children: [
            { path: '', component: PatientsComponent },
            { path: ':id', component: PatientDetailsComponent }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MainLayoutRoutingModule {}
