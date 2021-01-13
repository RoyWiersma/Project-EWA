import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/login.service';
import {GP} from '../../models/GP';
import {HttpClient} from '@angular/common/http';
import {Chat} from '../../models/Chat';
import {Message} from '../../models/Message';
import {format} from 'date-fns';

@Component({
    selector: 'app-chat',
    templateUrl: './chat.component.html',
    styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

    private readonly API_URL = 'http://localhost:8085/chat';

    isDoctor: boolean;
    chats: Chat[];
    messages: Message[];

    chatId: string;
    currentId: string;
    newMessage = new Message();

    constructor(public loginService: LoginService, private Server: HttpClient) { }

    ngOnInit(): void {
        console.log(this.messages);
        this.isDoctor = this.loginService.getLoggedInUser instanceof GP;
        this.Server.get(this.API_URL, {
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

        this.Server.get(`${this.API_URL}/${id}`, {
            headers: { authorization: localStorage.getItem('jwt') || null }
        }).subscribe((messages: Message[]) => {
            this.messages = messages.map(message => {
                return { ...message, dateTime: format(new Date(message.dateTime), 'dd-MM-yyyy kk:mm:ss') };
            });
        });
    }

    sendMessage(): void {
        this.newMessage.dateTime = new Date().toISOString();

        this.Server.post(`${this.API_URL}/${this.currentId}/message`, JSON.stringify(this.newMessage), {
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
