import { Component, OnInit, Inject, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { AlertServiceService } from 'src/app/services/alert-service.service';
import { LOGIN_SUCCESS } from 'src/app/store/reducers/loginReducer';

 
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup;
    loading = false;
    submitted = false;

    loginFailure: boolean;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private alertService: AlertServiceService) {}

    ngOnInit() {

         
            
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });

       this.router.navigate(['/profile'])
    
    }

    // convenience getter for easy access to form fields
    get f() { return this.loginForm.controls; }

    resetLoginFailure(): void {
        this.loginFailure = false;
    }

    login() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        this.loading = true;
        this.authenticationService.login(this.f.username.value, this.f.password.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.loginFailure = false;
                    // Update the store value, so that header can read the store value
                    let action = {'type': LOGIN_SUCCESS, isUserLoggedIn: true};
                    this.authenticationService.updateLoginState(action);
                    this.router.navigate(['/profile'])
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                    this.loginFailure = true;
                });
               ;
              
    
        

    }

}
