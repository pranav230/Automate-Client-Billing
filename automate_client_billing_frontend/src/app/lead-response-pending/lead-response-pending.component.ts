import { Component, Input, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2';
import { ServiceClassService } from '../service-class.service';

@Component({
  selector: 'app-lead-response-pending',
  templateUrl: './lead-response-pending.component.html',
  styleUrls: ['./lead-response-pending.component.scss']
})
export class LeadResponsePendingComponent implements OnInit {

  @Input() info;
  @Input() month;
  @Input() year;
  constructor(private swService:ServiceClassService,private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    console.log("Welcome to FICO Saurabh.");
  }

  sendReminderToLead(event){
    event.stopPropagation();
    console.log(event);

    this.spinner.show();
    this.swService.sendReminderMailToLead(this.info.projectID,this.month,this.year)
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
          title: `Successfully Send`
        })
        this.info.teamLeadReminder += 1;
        // setTimeout(this.redirectPage, 3000);
      },
      (error)=>{
        console.log(error);
      }
    )
  }

}
