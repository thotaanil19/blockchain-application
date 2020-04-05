import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  role: string;

  constructor(private router: Router) { 
    let currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    this.role = currentUser.role;
  }

  ngOnInit() {
    let currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    this.role = currentUser.role;
  }

}
