import { Component, Input, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2';
import { ServiceClassService } from '../service-class.service';

@Component({
  selector: 'app-client-rejected',
  templateUrl: './client-rejected.component.html',
  styleUrls: ['./client-rejected.component.scss']
})
export class ClientRejectedComponent implements OnInit {

  @Input() rejectedlist:any;
  @Input() month:any;
  @Input() year:any;
  @Input() searchValue:any;

  r:number=1;

  comment:string="";

  constructor(private swService:ServiceClassService,private spinner: NgxSpinnerService) {

   }

  ngOnInit(): void {
    this.rejectedlist = this.rejectedlist.filter(x => x.timestamp = this.timestampCalculation(x.timestamp));
  }

  showMessage(event,info){
    event.stopPropagation();
    this.spinner.show();
    this.swService.getRejectionComment(info.projectID,this.month,this.year)
    .subscribe(
      (response) => {
          if(response){
            this.spinner.hide();
            this.comment=response;
            console.log(this.comment);
            const swalWithBootstrapButtons = Swal.mixin({
              customClass: {
                confirmButton: 'btn btn-danger'
              },
              buttonsStyling: false
            })

            swalWithBootstrapButtons.fire({
              icon: 'warning',
              html: `<div style="margin-bottom:6px;"><Strong>Message For You</Strong></div> <div>${this.comment}</div>`,
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
