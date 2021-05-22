import { Component, Input, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2';
import { PaymentModel } from '../paymentModel';
import { ServiceClassService } from '../service-class.service';

@Component({
  selector: 'app-client-approved',
  templateUrl: './client-approved.component.html',
  styleUrls: ['./client-approved.component.scss']
})
export class ClientApprovedComponent implements OnInit {

  @Input() approvedlist:any;
  @Input() month:any;
  @Input() year:any;
  @Input() searchValue:any;

  summaryData= new PaymentModel;
  p:number=1;

  constructor(private swService: ServiceClassService,private spinner: NgxSpinnerService) {
   }

  ngOnInit(): void {
    this.approvedlist = this.approvedlist.filter(x => x.timestamp = this.timestampCalculation(x.timestamp));
  }

  getSummary(event,info){
    event.stopPropagation();
    this.spinner.show();
    this.swService.getTransactionSummary(info.projectID,this.month,this.year)
    .subscribe(
      (response) => {
          if(response){
            this.spinner.hide();
            this.summaryData=<PaymentModel>response;
            const d = new Date( this.summaryData.timestamp);
            const swalWithBootstrapButtons = Swal.mixin({
              customClass: {
                confirmButton: 'btn btn-primary'
              },
              buttonsStyling: false
            })

            swalWithBootstrapButtons.fire({
              icon: 'success',
              title: "Transaction Summary",
              html: `<div style="font-size: 0.9rem;">
              <strong style="margin-right: 20px;">Transaction ID:</strong>${this.summaryData.id}<br></br>
              <strong style="margin-right: 20px;">Payment Type:</strong>${this.summaryData.mode}<br></br>
              <strong style="margin-right: 20px;">Payment Tool:</strong>${this.summaryData.modeType}<br></br>
              <strong style="margin-right: 20px;">Payee Name:</strong>${this.summaryData.payeeName}<br></br>
              <strong style="margin-right: 20px;">Amount Paid:</strong>${this.summaryData.amount}<br></br>
              <strong style="margin-right: 20px;">Transaction Time:</strong>${d.toDateString()}<br></br>
              <strong>Message For You</strong>
              <div style="margin-top: 8px;">${this.summaryData.clientFinanceComment}<div></div>`,
              showClass: {
                popup: 'animated fadeInDown faster',
                icon: 'animated heartBeat delay-1s'
              },
              hideClass: {
                popup: 'animated fadeOutUp faster',
              }

            })
          }
        }
      );
  }

  timestampCalculation(data)
  {
    let currDate=new Date();
    let oneday = 1000 * 60 * 60 * 24;
    const d1 = new Date(data);
    var d = new Date( d1.getUTCFullYear(), d1.getUTCMonth(), d1.getUTCDate(), d1.getUTCHours(), d1.getUTCMinutes(), d1.getUTCSeconds() );
    var reminder=Math.abs(currDate.getTime()-d.getTime())/3600000;
    // console.log(d,reminder);
    if(reminder>24)
    {
      var month = d.toLocaleString('default', { month: 'short' });
      var day = d.getUTCDate();
      var year = d.getUTCFullYear();
      return day + "-" + month + "-" + year;
    }
    else
    {
      if(reminder<1)
      {
        reminder=reminder*60;
        if(reminder<0.6)
        {
          reminder=reminder*60;
          return Math.round(reminder) + " seconds ago";
        }
        else
        {
          return Math.round(reminder) + " minutes ago";
        }
      }
      else
      {
        return Math.round(reminder) + " hours ago";
      }
    }
  }
}



