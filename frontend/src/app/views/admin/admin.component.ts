import { Component, OnInit } from '@angular/core';
import {AdminService} from '../../services/admin.service';
import {Observable} from 'rxjs';
import {GP} from '../../models/GP';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
    allAdmin: GP[];
    allNoAdmin: GP[];
    gps: GP[];

    constructor(private service: AdminService) { }

  ngOnInit(): void {
        this.service.getAllGp().subscribe(
            (results: GP[]) => {
                this.allAdmin = results.filter(result => !!result.isAdmin);
                this.allNoAdmin = results.filter(result => !result.isAdmin);
            });
  }

  removeAdmin(admin: GP): void{
        this.service.setNoAdmin(admin).subscribe(
            (results: GP) => {
                this.ngOnInit();
            }
        );
  }

  giveAdmin(gp: GP): void {
        this.service.setAdmin(gp).subscribe(
            (results: GP) => {
                this.ngOnInit();
            }
        );
  }
}
