import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';

import { environment } from 'src/environments/environment.prod';
import { map } from 'rxjs/operators';
import { Block } from '../components/dto/Block';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Advertisement } from '../components/dto/Advertisement';

@Injectable({
  providedIn: 'root'
})
export class AdvertisementsService {

  url: string = environment.apiUrl;
  
  constructor(private http: HttpClient, private router: Router) { }


  

  save (advertisement : any) : Observable<boolean> {   
    if (!(sessionStorage.getItem('currentUser') ||
    JSON.parse(sessionStorage.getItem('currentUser')) ||
    JSON.parse(sessionStorage.getItem('currentUser')).token)) {
      this.router.navigate(['/login'])
    }
    const header = { Authorization: JSON.parse(sessionStorage.getItem('currentUser')).token };
    return this.http.post<boolean>( `${this.url}/advertisements`, advertisement, {headers: header})
    
  }

  delete (advertisement: Advertisement) : Observable<boolean> {

    if (!(sessionStorage.getItem('currentUser') ||
    JSON.parse(sessionStorage.getItem('currentUser')) ||
    JSON.parse(sessionStorage.getItem('currentUser')).token)) {
      this.router.navigate(['/login'])
    }

    let currentUser = JSON.parse(sessionStorage.getItem('currentUser'));

    
    

    if (currentUser.socialInsuranceNumber == advertisement.sellerSocialInsuranceNumber) {
     
      advertisement.active = false;
      return this.save(advertisement); 
    
   
   /*     
     return this.http.post<boolean>( this.url+ '/advertisements/delete', advertisement, {headers: header})
      //return this.http.post<any>( this.url +'/advertisements/' + advertisement.id, {}, {headers: header})
      */
    }  else {
      const observable = new Observable<boolean>((data) => {
        data.next(false);
        });
        return observable;
    }
  
  }

  get () : Observable<Advertisement[]> {    
    if (!(sessionStorage.getItem('currentUser') ||
    JSON.parse(sessionStorage.getItem('currentUser')) ||
    JSON.parse(sessionStorage.getItem('currentUser')).token)) {
      this.router.navigate(['/login'])
    }
     const header = { Authorization: JSON.parse(sessionStorage.getItem('currentUser')).token };
    return this.http.get<Advertisement[] >(`${this.url}/advertisements`, {headers: header});

   }
   
}
