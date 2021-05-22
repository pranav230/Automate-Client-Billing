import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2';
import { ClientEmailList } from '../clientEmailModel';
import { ServiceClassService } from '../service-class.service';

@Component({
  selector: 'app-all-details',
  templateUrl: './all-details.component.html',
  styleUrls: ['./all-details.component.scss']
})
export class AllDetailsComponent implements OnInit {
  activatedRoute: ActivatedRoute;
  id:any;
  swService:ServiceClassService;
  details:any;
  month:string;
  year:string;
  clientEmailList = new ClientEmailList();
  commentbox:any;
  sentStatus=false;
  disableClientButton=false;
  constructor(swService: ServiceClassService,activatedRoute: ActivatedRoute,private spinner: NgxSpinnerService) {
    this.activatedRoute=activatedRoute;
    this.swService=swService;
   }

  ngOnInit(): void {
    this.spinner.show();
    this.activatedRoute.params.subscribe(
      (params) => {
        this.id=params.id;
        this.month=params.month;
        this.year=params.year;
      }
    );


    this.swService.getAllDetails(this.id,this.month,this.year)
    .subscribe(
      (response) => {
          if(response){
            this.spinner.hide();
          }
          this.swService.teamDetails=response;
          this.details=this.swService.teamDetails;
          if(this.details.clientPaymentStatus!=null){
            this.disableClientButton=true;
          }
          // if(this.details.clientPaymentStatus!=null || !this.sentStatus)
          // {
          //   console.log("color change");
          //   document. getElementById("btnExcel").setAttribute('disabled', 'disabled');
          // }
        }
      );

  }

  sendEmail(){
    if(this.details.clientPaymentStatus==null)
    {
      this.clientEmailList.projectId=this.id;
      this.clientEmailList.toClientComment=this.commentbox;
      this.spinner.show();
      this.swService.sendClientPayment(this.clientEmailList,this.month,this.year)
      .subscribe(
        (res)=>{
          this.sentStatus=true;
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
            title: `Details Successfully sent to Client.`
          })
          setTimeout(this.redirectPage, 3000);
        }
      );
    }
  }

  redirectPage(){
    window.location.href = '/client';
  }
}

