import { Component, OnInit } from '@angular/core';
import {MatTabsModule} from '@angular/material/tabs';
import { ViewEncapsulation } from '@angular/core';
import { ServiceClassService } from '../service-class.service';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-client-payment',
  templateUrl: './client-payment.component.html',
  styleUrls: ['./client-payment.component.scss'],
  // encapsulation: ViewEncapsulation.None
})
export class ClientPaymentComponent implements OnInit {

  swService : ServiceClassService;
  approvedClient = [];
  searchValue:any;
  item:any;
  approvedValue:any=true;
  pendingValue:any=true;
  rejectedValue:any=true;
  activatedRoute: ActivatedRoute;
  clientData:any;
  calMonth=new Date().getMonth()+1;
  calYear=new Date().getFullYear();
  calender=this.calYear+'-'+('0' + this.calMonth).slice(-2);
  calenderValue= this.calYear+'-'+('0' + this.calMonth).slice(-2);
  month:string;
  year:string;
  approvedlist;
  rejectedlist;
  pendinglist;
  view=false;

  constructor(swService : ServiceClassService,http:HttpClient,private spinner: NgxSpinnerService,activatedRoute: ActivatedRoute) {
    this.swService=swService;
    this.activatedRoute=activatedRoute;

   }

  ngOnInit(): void {
    this.loadPageAccordingMonth();
    // this.loadPageAccordingMonth();
    // console.log(this.item);
  }

  boxChangeRequired(data){
    console.log(this.approvedlist);
    this.item=this.approvedClient.filter(x=>
      (x.clientName.toLowerCase().indexOf(data.toLowerCase()) !== -1 ||
      x.projectName.toLowerCase().indexOf(data.toLowerCase()) !== -1 ||
      x.feedback.toLowerCase().indexOf(data.toLowerCase()) !== -1)
    );

    if(data==""|| data==null)
    {
      this.approvedValue=true;
      this.pendingValue=true;
      this.rejectedValue=true;
    }
    else{
      if(this.item.filter(x=>x.feedback=='Approved')!=0){
        this.approvedValue=true;
      }
      else{
        this.approvedValue=false;
      }

      if(this.item.filter(x=>x.feedback=='Pending')!=0){
        this.pendingValue=true;
      }
      else{
        this.pendingValue=false;
      }

      if(this.item.filter(x=>x.feedback=='Rejected')!=0){
        this.rejectedValue=true;
      }
      else{
        this.rejectedValue=false;
      }
    }
  }

  change(){
    // this.p=1;
  }

  filterItemsOfType(){
    let t1;
    t1 = this.approvedClient.filter(x => x.feedback == 'Approved');
    console.log(t1);
    console.log(this.approvedClient);
    return t1;
  }

  loadPageAccordingMonth(){
    this.spinner.show();
    this.year=this.calenderValue.substring(0,4);
    this.month=this.calenderValue.substring(5,7);
    this.swService.getAllClientData(this.month,this.year)
    .subscribe(
      (response) => {
          if(response){
            this.spinner.hide();
            this.swService.clientPaymentRecord=response;
            this.approvedClient=this.swService.clientPaymentRecord;
            this.approvedlist = this.approvedClient.filter(x => x.feedback == 'Approved');
            this.pendinglist = this.approvedClient.filter(x => x.feedback == 'Pending');
            this.rejectedlist = this.approvedClient.filter(x => x.feedback == 'Rejected');
            this.view=true;
          }
        }
      );
  }
}
