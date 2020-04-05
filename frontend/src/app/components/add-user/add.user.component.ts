import { Component, OnInit, Input, Output } from '@angular/core';
import { BlockchainService } from 'src/app/services/blockchain.service';
import { Registration } from '../dto/Registration';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add.user.component.html',
  styleUrls: ['./add.user.component.css']
})
export class AddUserComponent implements OnInit {

  @Input()
  @Output()
  name: string;
  @Input()
  @Output()
  email: string; 
  @Input()
  @Output()
  phone: string;  
  
  @Input()
  @Output()
  socialInsuranceNumber: string;

  @Output()
  saveStatus: boolean = false;
  @Output()
  errorMessage: string;

  addUserForm: FormGroup;
  submitted = false;

  constructor(private authenticationService: AuthenticationService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.addUserForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      socialInsuranceNumber: ['', Validators.required]
    });
  }

  get f() { return this.addUserForm.controls; }

  register(): void {
    let user = {
      name: this.name,
      email: this.email,
      phone: '+1 '+this.phone,
      isActive: true,
      username: this.email,
      password: this.email,
      socialInsuranceNumber : this.socialInsuranceNumber,
      role: 'REGISTRATION_OFFICER'
    };

    this.submitted = true;

    // stop here if form is invalid
    if (this.addUserForm.invalid) {
      return;
    }
    this.authenticationService.register(user).subscribe(data => {
      if (data) {
        this.cancel();
      }
      this.saveStatus = data;
    }, error => {
      this.errorMessage = error.error;
      this.saveStatus = false;
    });
  }


  cancel(): void {
    this.name = null;
    this.email = null; 
    this.phone = null; 
    this.submitted = false;
    this.errorMessage = null;
    this.addUserForm.reset();
  }



}
