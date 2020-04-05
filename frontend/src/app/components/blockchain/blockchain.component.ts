import { Component, OnInit, Input, Output } from '@angular/core';
import { Block } from '../dto/Block';
import { BlockchainService } from 'src/app/services/blockchain.service';
import { Registration } from '../dto/Registration';

@Component({
  selector: 'app-blockchain',
  templateUrl: './blockchain.component.html',
  styleUrls: ['./blockchain.component.css']
})
export class BlockchainComponent implements OnInit {

  blocks : Block[];
  filteredResults : Block[];

  pagedItems: any[];


  @Input()
  searchText: string;

  constructor(private blockchainService: BlockchainService) { 
   }

  ngOnInit() {
    this.blockchainService.get().subscribe(data => {
      this.blocks = data;
      this.filteredResults = data;
      this.onChangePage(this.filteredResults);
    });

  }

  filterTheResults () {
    if (this.searchText) {
      this.searchText = this.searchText.toLowerCase();
      this.filteredResults = this.blocks.filter(
        block => (this.startsWithFun(block.data.surveyNumber, this.searchText)  
        || this.startsWithFun(block.data.sellerName.toLowerCase(), this.searchText) 
        || this.startsWithFun(block.data.buyerName.toLowerCase(), this.searchText)   
        || this.startsWithFun(block.data.price, this.searchText)   
        || this.startsWithFun(block.data.propertyAddressCity.toLowerCase(), this.searchText)  
        || this.startsWithFun(block.data.propertyType.toLowerCase(), this.searchText) 
        || this.startsWithFun(block.data.propertyAddressState.toLowerCase(), this.searchText)     
        || this.startsWithFun(block.registerationDoneBy, this.searchText)        
        )      
         );
      } else {
        this.filteredResults = this.blocks;
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



}
