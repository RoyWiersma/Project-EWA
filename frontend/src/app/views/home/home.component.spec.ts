import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {HomeComponent} from './home.component';
import {LoginService} from '../../services/login.service';
import {HttpClientModule} from '@angular/common/http';
import {RouterTestingModule} from '@angular/router/testing';

describe('HomeComponent', () => {
    let component: HomeComponent;
    let html: HTMLElement;
    let fixture: ComponentFixture<HomeComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [HomeComponent],
            imports: [
                RouterTestingModule,
                HttpClientModule
            ],
            providers: [
                LoginService
            ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(HomeComponent);
        component = fixture.componentInstance;
        html = fixture.debugElement.nativeElement;
        fixture.detectChanges();
    });

    fit('should create', () => {
        expect(component).toBeTruthy();
    });


    fit('should return error when button is clicked without input', () => {
        // Retrieve the modalButton by id
        const modalButton: HTMLButtonElement = html.querySelector('#loginButton');
        // Open the modal
        modalButton.click();

        // Retrieve the loginButton by id
        const loginButton: HTMLButtonElement = html.querySelector('#form-login');

        // Click login button with no values given and expect it to throw an error
        expect(loginButton.click).toThrowError();
    });

    fit('When page is loaded the input values should be empty', async(() => {
        expect(component.email && component.password).toEqual(undefined);
    }));

});
