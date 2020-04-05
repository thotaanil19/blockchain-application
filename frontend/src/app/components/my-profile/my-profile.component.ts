import { Component, OnInit, Input, Output } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';



@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService,
     private router: Router,
     private formBuilder: FormBuilder) { }

  loginId: string;
  name: string;
  email: string;
  phone: string;
  role: string;
  isActive: boolean = true;

  @Input()
  password: string;
  @Input()
  newPassword: string;
  @Input()
  confirmPassword: string;
  
  @Output()
  changePasswordStatus: boolean;

  @Output()
  changePasswordSubmitted: boolean;
  changePasswordForm: FormGroup;


  private currentUser : any;

  ngOnInit() {
    this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    this.loginId = this.currentUser.loginId;
    this.email = this.currentUser.email;
    this.phone = this.currentUser.phone;
    this.name = this.currentUser.name;
    this.role = this.currentUser.role;

    this.changePasswordForm = this.formBuilder.group({
      password: ['', Validators.required],
      newPassword: ['', Validators.required],
      confirmPassword: ['', Validators.required] 
    }
);
}
 

 
get f() { return this.changePasswordForm.controls; }

updatePassword() {

    if (sessionStorage.getItem('currentUser') == null) {
      this.router.navigate(['/login']);
    }
    let currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    let username = currentUser.loginId;

    this.changePasswordSubmitted = true;

    if (this.password == this.newPassword || this.newPassword != this.confirmPassword) {
      this.changePasswordStatus = false;
      return;
    }
  
    this.authenticationService.changePassword(username, this.password, this.newPassword)
    .pipe(first())
            .subscribe(
                data => {
                    this.changePasswordStatus = true;
                    this.password = null;
                    this.newPassword = null;
                    this.confirmPassword = null;
                   // this.router.navigate(['/profile'])
                },
                error => {
                     this.changePasswordStatus = false;
                 });;
  }

  cancelUpdatePassword() {
    this.password = null;
    this.confirmPassword = null;
    this.changePasswordSubmitted = null;
    this.changePasswordStatus = null;
    this.changePasswordForm.reset();

  }

}
