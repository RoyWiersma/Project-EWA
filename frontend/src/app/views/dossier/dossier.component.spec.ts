import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {DossierComponent} from './dossier.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormsModule} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';

describe('DossierComponent', () => {
    let component: DossierComponent;
    let fixture: ComponentFixture<DossierComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ DossierComponent ],
            imports: [
                HttpClientTestingModule,
                FormsModule,
                RouterTestingModule
            ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DossierComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
