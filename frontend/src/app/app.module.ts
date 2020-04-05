import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { Route, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { AboutComponent } from './components/about/about.component';
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { BlockchainComponent } from './components/blockchain/blockchain.component';
import { AuthGuardService } from './services/guards/auth-guard.service';
import { AddUserComponent } from './components/add-user/add.user.component';
import { SignUpComponent } from './components/signup/signup.component';
import { AdvertisementsComponent } from './components/advertisements/advertisements.component';
import { AddAdvertisementsComponent } from './components/add-advertisement/add.advertisements.component';
import { PaginationComponent } from './components/common/pagination/pagination.component';

import {StoreModule} from '@ngrx/store';

import {reducers} from './store/reducers/reducers'
 

const ROUTES: Route[] = [
  { path: '', component: LoginComponent},
  { path: 'login', component: LoginComponent},
  { path: 'signup', component: SignUpComponent},
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuardService]},
  { path: 'about', component: AboutComponent, canActivate: [AuthGuardService]},
  { path: 'profile', component: MyProfileComponent, canActivate: [AuthGuardService]},
  { path: 'registration', component: RegistrationComponent, canActivate: [AuthGuardService]},
  { path: 'blockchain', component: BlockchainComponent, canActivate: [AuthGuardService]},
  { path: 'addUser', component: AddUserComponent, canActivate: [AuthGuardService]},
  { path: 'advertisements', component: AdvertisementsComponent, canActivate: [AuthGuardService]},
  { path: 'addadvertisement', component: AddAdvertisementsComponent, canActivate: [AuthGuardService]}
]


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent,
    DashboardComponent,
    HeaderComponent,
    FooterComponent,
    AboutComponent,
    MyProfileComponent,
    RegistrationComponent,
    BlockchainComponent,
    AddUserComponent,
    AdvertisementsComponent,
    AddAdvertisementsComponent,
    PaginationComponent
   ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    StoreModule.forRoot(reducers, {}),
    RouterModule.forRoot(ROUTES)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 
  
  
}