import { Component, OnInit, ViewChild} from '@angular/core';
import {MatAccordion} from '@angular/material/expansion';
import { ServiceClassService } from '../service-class.service';
import { BillingData } from '../billingModel';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-right-panel',
  templateUrl: './right-panel.component.html',
  styleUrls: ['./right-panel.component.scss']
})
export class RightPanelComponent implements OnInit {

  private swService :ServiceClassService;
  billSearch: string;
  projectList: BillingData[];
  p:number = 1;
  calMonth=new Date().getMonth()+1;
  calYear=new Date().getFullYear();
  calender=this.calYear+'-'+('0' + this.calMonth).slice(-2);
  calenderValue=this.calYear+'-'+('0' + this.calMonth).slice(-2);
  month:string;
  year:string;

  filterDay:any;



  constructor(swService : ServiceClassService,private spinner: NgxSpinnerService) {
    this.swService = swService;
   }

  ngOnInit(): void {
    this.loadPageAccordingMonth();
  }
  changePaginatorOnSearch(){
    this.p=1;
  }



  loadPageAccordingMonth(){
    this.spinner.show();
    this.year=this.calenderValue.substring(0,4);
    this.month=this.calenderValue.substring(5,7);

    this.filterDay = [this.month,this.year];

    // console.log(this.filterDay);

    this.swService.getAllData(this.month,this.year)
    .subscribe(
    (response) => {
        if(response){
          this.spinner.hide();
        }
        this.swService.incom=<BillingData[]>response;
        this.projectList=this.swService.incom;

        this.projectList.sort((a,b) => {
          if(a.leadStatus == null && b.leadStatus != null){
            return -1;
          }
          if(b.leadStatus != null && a.leadStatus == null){
            return 1;
          }
          return 0;
        });
      }
    );
  }

  @ViewChild(MatAccordion) accordion: MatAccordion;

}
