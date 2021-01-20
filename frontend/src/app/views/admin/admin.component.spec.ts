import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AdminComponent} from './admin.component';
import {AdminService} from '../../services/admin.service';
import {HttpClientModule} from '@angular/common/http';
import {AdminMockService} from '../../mocks/admin-mock.service';
import {DebugElement} from "@angular/core";

describe('AdminComponent', () => {
    let component: AdminComponent;
    let fixture: ComponentFixture<AdminComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AdminComponent],
            imports: [
                HttpClientModule,
            ],
            providers: [
                {
                    provide: AdminService,
                    useClass: AdminMockService
                }
            ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AdminComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('adminTable should have 5 admin', () => {
        const dbElement: DebugElement = fixture.debugElement;
        const tableRows: HTMLTableRowElement[] = dbElement.nativeElement.querySelectorAll('#adminTable tr');

        // 5 + 1 for table row at top for naming
        expect(tableRows.length).toEqual(6);
    });

    it('noAdminTable should have 5 gp', () => {
        const dbElement: DebugElement = fixture.debugElement;
        const tableRows: HTMLTableRowElement[] = dbElement.nativeElement.querySelectorAll('#noAdminTable tr');

        // 5 + 1 for table row at top for naming
        expect(tableRows.length).toEqual(6);
    });
});
