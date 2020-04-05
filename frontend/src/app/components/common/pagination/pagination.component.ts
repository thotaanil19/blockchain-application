import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
 
@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit {


  pageNumber: number= 1;
  pageSize: number = 5;

  @Input()
  data: any[];

  @Output()
  paginatedResults: any[];

  @Output() changePage = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  loadNextPageResults = () => {
    if ((this.pageNumber-1)*this.pageSize < this.data.length) {
      this.pageNumber++;
      this.pagenateResults();    
  }
}

  loadPreviousResults = () => {
   if (this.pageNumber >1) {
      this.pageNumber--;
      this.pagenateResults();
    }

  }
  pagenateResults = () => {
    let start = (this.pageNumber-1)*this.pageSize;
    this.paginatedResults = this.data.slice(start, start+this.pageSize);
    if (this.paginatedResults.length != 0) {
      this.changePage.emit(this.paginatedResults);
    } else {
      this.pageNumber--;
    }
   
  }

}
