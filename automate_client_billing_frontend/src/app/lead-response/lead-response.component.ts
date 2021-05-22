import { Component, OnInit } from '@angular/core';
import { ServiceClassService } from '../service-class.service';
import { HttpClient } from '@angular/common/http';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-lead-response',
  templateUrl: './lead-response.component.html',
  styleUrls: ['./lead-response.component.scss'],
})
export class LeadResponseComponent implements OnInit {
  swService: ServiceClassService;
  teamLeaddata: any;
  searchValue: any;
  item: any;
  approvedValue: any = true;
  pendingValue: any = true;
  rejectedValue: any = true;
  activatedRoute: ActivatedRoute;
  id: any;
  amount: any;
  calMonth=new Date().getMonth()+1;
  calYear=new Date().getFullYear();
  calender=this.calYear+'-'+('0' + this.calMonth).slice(-2);
  calenderValue=this.calYear+'-'+('0' + this.calMonth).slice(-2);
  month:string;
  year:string;

  constructor(
    swService: ServiceClassService,
    http: HttpClient,
    private spinner: NgxSpinnerService,
    activatedRoute: ActivatedRoute
  ) {
    this.swService = swService;
    this.activatedRoute = activatedRoute;
  }

  ngOnInit(): void {
    this.loadPageAccordingMonth();
  }

  boxChangeRequired(data) {
    this.item = this.teamLeaddata.filter(
      (x) =>
        x.clientName.toLowerCase().indexOf(data.toLowerCase()) !== -1 ||
        x.teamLeadname.toLowerCase().indexOf(data.toLowerCase()) !== -1 ||
        x.teamLeadID.toLowerCase().indexOf(data.toLowerCase()) !== -1 ||
        x.projectName.toLowerCase().indexOf(data.toLowerCase()) !== -1 ||
        x.feedback.toLowerCase().indexOf(data.toLowerCase()) !== -1
    );

    if (this.item.filter((x) => x.feedback == 'Approved') != 0) {
      this.approvedValue = true;
    } else {
      this.approvedValue = false;
    }

    if (this.item.filter((x) => x.feedback == 'Pending') != 0) {
      this.pendingValue = true;
    } else {
      this.pendingValue = false;
    }

    if (this.item.filter((x) => x.feedback == 'Rejected') != 0) {
      this.rejectedValue = true;
    } else {
      this.rejectedValue = false;
    }
  }

  filterItemsOfType(Data) {
    return this.teamLeaddata.filter((x) => x.feedback == Data);
  }

  loadPageAccordingMonth(){
    this.spinner.show();
    this.year=this.calenderValue.substring(0,4);
    this.month=this.calenderValue.substring(5,7);
    this.swService.getAllLeadData(this.month,this.year)
    .subscribe((response) => {
      if (response) {
        this.spinner.hide();
      }
      this.swService.teamResponseRecord = response;
      this.teamLeaddata = this.swService.teamResponseRecord;
    });
  }
}
