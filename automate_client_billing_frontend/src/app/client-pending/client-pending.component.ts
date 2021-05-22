import { Component, Input, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2';
import { ServiceClassService } from '../service-class.service';

@Component({
  selector: 'app-client-pending',
  templateUrl: './client-pending.component.html',
  styleUrls: ['./client-pending.component.scss']
})
export class ClientPendingComponent implements OnInit {

  @Input() pendinglist:any;
  @Input() month:any;
  @Input() year:any;
  @Input() searchValue:any;

  q:number=1;
  constructor(private swService:ServiceClassService,private spinner: NgxSpinnerService) {

   }

  ngOnInit(): void {
    this.pendinglist = this.pendinglist.filter(x => x.timestamp = this.timestampCalculation(x.timestamp));
  }

  sendReminderToClient(event, info){

    event.stopPropagation();
    this.spinner.show();
    this.swService.sendEmailToClientForReminder(this.month,this.year,info.projectID)
    .subscribe((result) => {
      this.spinner.hide();
      const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.addEventListener('mouseenter', Swal.stopTimer)
          toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
      })
      Toast.fire({
        icon: 'success',
        title: `Reminder Send Successfully`
      })
      info.clientReminder+=1;
    },
    (error)=>{
      const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.addEventListener('mouseenter', Swal.stopTimer)
          toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
      })
      Toast.fire({
        icon: 'error',
        title: `Some Error Occured`
      })

    }
  )
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
