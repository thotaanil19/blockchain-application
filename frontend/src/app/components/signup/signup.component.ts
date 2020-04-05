import { Component, OnInit, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { BlockchainService } from 'src/app/services/blockchain.service';
import { Registration } from '../dto/Registration';
import { Signup } from '../dto/Signup';
import { SignupService } from 'src/app/services/signup.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignUpComponent implements OnInit {

  constructor(private router: Router, private formBuilder: FormBuilder,
    private signupService: SignupService) { }

  @Input()
  @Output()
  name: string;

  @Input()
  @Output()
  socialInsuranceNumber: string;

  @Input()
  @Output()
  email: string;

  //@Input()  @Output()  password: string;

  @Input()
  @Output()
  phone: string;

  signupForm: FormGroup;

  @Output()
  saveStatus: boolean = false;

  @Output()
  errorMessage: string;
  submitted = false;
  signupDto: Signup;

  ngOnInit() {
    this.signupForm = this.formBuilder.group({
      name: ['', Validators.required],
      socialInsuranceNumber: ['', Validators.required],
      email: ['', Validators.required],
      //password: ['', Validators.required],
      phone: ['', Validators.required]      
    });
  }

  get f() { return this.signupForm.controls; }

  signup(): void {
    this.signupDto = {
      name: this.name,
      socialInsuranceNumber: this.socialInsuranceNumber,
      email: this.email,
      username: this.email,
      //password: this.password,
      phone: this.phone,
      role: "PUBLIC_USER"
    };

    this.submitted = true;

    // stop here if form is invalid
    if (this.signupForm.invalid) {
      return;
    }
    

    this.signupService.save(this.signupDto).subscribe(data => {
      if (data) {
        this.cancel();
      }
      this.saveStatus = data;
    }, error => {
      this.errorMessage = error.error.message;
      this.saveStatus = false;

    });
  }


  cancel(): void {
    this.name = null;
    this.socialInsuranceNumber = null;
    this.email = null;
    this.phone = null;
    //this.password = null;
     
    this.saveStatus = null;
    this.submitted = false;
    this.errorMessage = null;
    this.signupForm.reset();
  }


}
