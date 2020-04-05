import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isUserLoggedIn: boolean;

  constructor(private authenticationService: AuthenticationService,  private router: Router) { 
  }

  ngOnInit() {
    this.authenticationService.getLoginState().subscribe(x => {
      this.isUserLoggedIn = x.isUserLoggedIn;
    });
    if(sessionStorage.getItem('currentUser')) {
      this.isUserLoggedIn = true;
    }
     /*this.authenticationService.getLoginSuccessMessageToHeaderSection()
     .subscribe(x => this.isUserLoggedIn = x);*/    

  }


logout() {
  // remove user from local storage to log user out
  this.authenticationService.logout();
  this.isUserLoggedIn = false;
  this.router.navigate(['/login']);
}

login() {
   this.router.navigate(['/login']);
}

about() {
  this.router.navigate(['/about']);
}

dashboard() {
  this.router.navigate(['/profile']);
}

 

}
