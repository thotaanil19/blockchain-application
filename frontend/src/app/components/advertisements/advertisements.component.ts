import { Component, OnInit, Input } from '@angular/core';
import { Block } from '../dto/Block';
import { BlockchainService } from 'src/app/services/blockchain.service';
import { Registration } from '../dto/Registration';
import { AdvertisementsService } from 'src/app/services/advertisements.service';
import { Advertisement } from '../dto/Advertisement';
import { User } from '../dto/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-advertisements',
  templateUrl: './advertisements.component.html',
  styleUrls: ['./advertisements.component.css']
})
export class AdvertisementsComponent implements OnInit {

  advertisements : Advertisement[];

  filteredResults : Advertisement[];

  pagedItems: any[];

  socialInsuranceNumber: string;

  @Input()
  searchText: string;

  constructor(private advertisementsService: AdvertisementsService, private router: Router) { 
  }

  ngOnInit() {
    let currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    this.socialInsuranceNumber = currentUser.socialInsuranceNumber;
    this.advertisementsService.get().subscribe(data => {
      this.advertisements = data;
      this.filteredResults = data;
      this.onChangePage(this.filteredResults);
    });
  }

  filterTheResults () {
    if (this.searchText) {
      this.searchText = this.searchText.toLowerCase();
      this.filteredResults = this.advertisements.filter(
        advertisement => (this.startsWithFun(advertisement.surveyNumber, this.searchText)  
        || this.startsWithFun(advertisement.sellerName.toLowerCase(), this.searchText) 
        || this.startsWithFun(advertisement.sellerPhone.toLowerCase(), this.searchText)   
        || this.startsWithFun(advertisement.price, this.searchText)   
        || this.startsWithFun(advertisement.surveyNumber.toLowerCase(), this.searchText)  
        || this.startsWithFun(advertisement.propertyType.toLowerCase(), this.searchText) 
        || this.startsWithFun(advertisement.propertyAddressState.toLowerCase(), this.searchText)     
        )      
         );
      } else {
        this.filteredResults = this.advertisements;
      }
      this.onChangePage(this.filteredResults);
  }

  startsWithFun (data, filter) : boolean{
    return (''+data).startsWith(filter);
  }

  onChangePage(pagedItems: Array<any>) {
    if (pagedItems && pagedItems.length > 5) {
      this.pagedItems = pagedItems.slice(0, 5);
    } else {
      this.pagedItems = pagedItems;
    }
  }

  deleteAdvertisement = (advertisement: Advertisement) => {
    this.advertisementsService.delete(advertisement).subscribe(res => {
      if (res) {
        //this.router.navigate(['/advertisements'])
        this.advertisements = this.advertisements.filter(ad => ad.id != advertisement.id);
        this.filterTheResults();
      }
    });
  }




}
