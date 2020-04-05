import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment.prod';
import { Observable, Subject } from 'rxjs';
import { User } from '../components/dto/User';
import { Store } from '@ngrx/store';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
 
  private subject = new Subject<any>();
  url: string = environment.apiUrl;
  isUserLoggedIn: boolean = false;


  constructor(private http: HttpClient, private store: Store<any>) {
    if(sessionStorage.getItem('currentUser')) {
      this.isUserLoggedIn = true;
    }
   }

  ngOnInit() {  }
  

  login(username: string, password: string) { 

    
      return this.http.post<any>(this.url + '/user/login', { username: username, password: password })
      .pipe(map(usr =>
        {
          // login successful if there's a jwt token in the response
          if (usr) {
              // store user details and jwt token in local storage to keep user logged in between page refreshes
              let user : User = {
                loginId: usr.loginId,
                name: usr.name,
                email: usr.email,
                phone: usr.phone,
                isActive: usr.isActive,
                token: usr.token,
                role: usr.role,
                socialInsuranceNumber: usr.socialInsuranceNumber
              };
              sessionStorage.setItem('currentUser', JSON.stringify(user));
              this.isUserLoggedIn = true;
              //this.sendLoginSuccessMessageToHeaderSection(true);
          }

          return usr;
          }));
  }

  register(user:any) { 
    const header = { Authorization: JSON.parse(sessionStorage.getItem('currentUser')).token };  
    return this.http.post<any>( `${this.url}/user/register`, user, {headers: header});     
}

  logout() {
      // remove user from local storage to log user out
      sessionStorage.removeItem('currentUser');
      this.isUserLoggedIn = false;
  }

  changePassword(username: string, password: string, newPassword: string) {

    const header = { Authorization: JSON.parse(sessionStorage.getItem('currentUser')).token };

    return this.http.post<any>(this.url + '/user/changePassword', 
    { username: username, password: password, newPassword: newPassword },
    {headers: header})
    .pipe(map(usr => 
        {
           
            // login successful if there's a jwt token in the response
            if (usr) {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                let user: User = {
                  loginId: usr.loginId,
                  name: usr.name,
                  email: usr.email,
                  phone: usr.phone,
                  isActive: usr.isActive,
                  token: usr.token,
                  role: usr.role,
                  socialInsuranceNumber: usr.socialInsuranceNumber
                };
                sessionStorage.setItem('currentUser', JSON.stringify(user));
            }

            return usr;
        }));
  }

 /*getLoginSuccessMessageToHeaderSection(): Observable<any> {
     return this.subject.asObservable();
 }
 sendLoginSuccessMessageToHeaderSection(isUserLoggedIn: any) {
   this.subject.next({ isUserLoggedIn });
 }*/

getLoginState() {
  return this.store.select('loginReducer');
}

updateLoginState (action) {
  this.store.dispatch(action);
}

}
