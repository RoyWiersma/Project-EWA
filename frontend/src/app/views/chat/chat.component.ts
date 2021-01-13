import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/login.service';
import {GP} from '../../models/GP';
import {HttpClient} from '@angular/common/http';
import {Chat} from '../../models/Chat';
import {Message} from '../../models/Message';
import {format} from 'date-fns';
import {environment} from '../../../environments/environment';

@Component({
    selector: 'app-chat',
    templateUrl: './chat.component.html',
    styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

    isDoctor: boolean;
    chats: Chat[];
    messages: Message[];

    chatId: string;
    currentId: string;
    messageCount: number;
    newMessage: Message;
    loaded: boolean;

    constructor(public loginService: LoginService, private Server: HttpClient) {
        this.messageCount = 0;
        this.loaded = false;
        this.newMessage = new Message();
    }

    ngOnInit(): void {
        this.isDoctor = this.loginService.getLoggedInUser instanceof GP;
        this.Server.get(`${environment.apiUrl}/chat`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        }).subscribe((chat: Chat[]) => {
            this.chats = chat;
        });
    }

    setChat(id: string): void {
        this.chatId = id;

        setInterval(() => {
            this.getMessages(this.chatId);
        }, 1000);
    }

    getMessages(id: string): void {
        this.currentId = id;

        this.Server.get(`${environment.apiUrl}/chat/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        }).subscribe((messages: Message[]) => {
            this.messages = messages.map(message => {
                return { ...message, dateTime: format(new Date(message.dateTime), 'dd-MM-yyyy kk:mm:ss') };
            });

            if (!this.loaded && this.messageCount > 0) {
                const chatElement = document.querySelector('#chat-messages .row');
                chatElement.scrollTop = chatElement.scrollHeight;
                this.loaded = true;
            }

            if (this.messages.length > this.messageCount) {
                this.messageCount = this.messages.length;
                const chatElement = document.querySelector('#chat-messages .row');
                chatElement.scrollTop = chatElement.scrollHeight;
            }
        });
    }

    sendMessage(): void {
        this.newMessage.dateTime = new Date().toISOString();

        this.Server.post(`${environment.apiUrl}/chat/${this.currentId}/message`, JSON.stringify(this.newMessage), {
            headers: {
                'Content-Type': 'application/json',
                authorization: localStorage.getItem('jwt') || null,
            }
        }).subscribe((message: any) => {
            this.messages.push(message.chatMessage);
            this.newMessage = new Message();
        });
    }
}
