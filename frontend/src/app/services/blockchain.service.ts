import { Injectable } from '@angular/core';
import { Registration } from '../components/dto/Registration';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';

import { environment } from 'src/environments/environment.prod';
import { map } from 'rxjs/operators';
import { Block } from '../components/dto/Block';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BlockchainService {

  url: string = environment.apiUrl;
  
  constructor(private http: HttpClient, private router: Router) { }


  save (registration : Registration) : Observable<boolean> {   
    if (!(sessionStorage.getItem('currentUser') ||
    JSON.parse(sessionStorage.getItem('currentUser')) ||
    JSON.parse(sessionStorage.getItem('currentUser')).token)) {
      this.router.navigate(['/login'])
    }
    const header = { Authorization: JSON.parse(sessionStorage.getItem('currentUser')).token };
    return this.http.post<boolean>( `${this.url}/registration`, registration, {headers: header})
    

  }

  get () : Observable<Block[]> {    
    if (!(sessionStorage.getItem('currentUser') ||
    JSON.parse(sessionStorage.getItem('currentUser')) ||
    JSON.parse(sessionStorage.getItem('currentUser')).token)) {
      this.router.navigate(['/login'])
    }
     const header = { Authorization: JSON.parse(sessionStorage.getItem('currentUser')).token };
    return this.http.get<Block[] >(`${this.url}/registration`, {headers: header});

   }
   
}
