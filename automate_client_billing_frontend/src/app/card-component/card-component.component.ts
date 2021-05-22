import { Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
import {PageEvent} from '@angular/material/paginator';
import { ServiceClassService } from '../service-class.service';
import {NgxPaginationModule} from 'ngx-pagination';
import { HttpClient } from '@angular/common/http';
import { NgxSpinnerService } from 'ngx-spinner';
import { Home } from '../homeModel';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-card-component',
  templateUrl: './card-component.component.html',
  styleUrls: ['./card-component.component.scss']
})
export class CardComponentComponent implements OnInit {

  searchValue:string;
  calMonth=new Date().getMonth()+1;
  calYear=new Date().getFullYear();
  calender=this.calYear+'-'+('0' + this.calMonth).slice(-2);
  calenderValue=this.calYear+'-'+('0' + this.calMonth).slice(-2);
  p:number=1;
  saveposition:number;
  projects:Home[];
  month:string;
  year:string;

  private http: HttpClient;
  swService: ServiceClassService;
  constructor(swService: ServiceClassService,http:HttpClient,private spinner: NgxSpinnerService) {
    this.swService=swService;
  }
  ngOnInit(): void {
   this.loadPageAccordingMonth();

   console.log(this.calenderValue);
  }

  changePaginatorOnSearch(){
      this.p=1;
  }

  loadPageAccordingMonth(){
    this.spinner.show();
    this.year=this.calenderValue.substring(0,4);
    this.month=this.calenderValue.substring(5,7);
    this.swService.getHome(this.month,this.year)
    .subscribe(
      (response) => {
          if(response){
            this.spinner.hide();
          }
          this.swService.projects=<Home[]>response;
          this.projects=this.swService.projects;
        }
      );
  }
}
