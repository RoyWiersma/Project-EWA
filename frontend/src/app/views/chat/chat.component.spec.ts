import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ChatComponent} from './chat.component';
import {LoginService} from '../../services/login.service';
import {HttpClientModule} from '@angular/common/http';

describe('ChatComponent', () => {
    let component: ChatComponent;
    let fixture: ComponentFixture<ChatComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ChatComponent],
            imports: [
                HttpClientModule
            ],
            providers: [
                LoginService
            ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ChatComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
