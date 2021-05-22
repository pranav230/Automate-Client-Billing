import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { faSubway } from '@fortawesome/free-solid-svg-icons';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2';
import { PaymentModel } from '../paymentModel';
import { PaymentPage } from '../paymentPageModel';
import { ServiceClassService } from '../service-class.service';

declare var paypal;
@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {

  @ViewChild('paypal', { static: true }) paypalElement: ElementRef;

  id;
  month;
  year;
  paidFor = false;
  paymentStruct = new PaymentModel();
  paymentStatus = new PaymentPage();
  comment:string = "";
  currData:any;

  constructor(private router:Router,private activatedRoute: ActivatedRoute,private swSerivice:ServiceClassService,private spinner: NgxSpinnerService)
  {

  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      (params) => {
        this.id = params.id;
        this.month=params.month;
        this.year=params.year;
      }
    );

    // console.log("Hi");
      this.spinner.show();
      this.swSerivice.getTotalPaymentStatus(this.id,this.month,this.year)
      .subscribe((result) => {
      // console.log(result);
      this.spinner.hide();
      this.paymentStatus.paymentStatus = result['clientStatus'];
      this.paymentStatus.totalPayment = result['totalPayment'];

      if(this.paymentStatus.paymentStatus == "Pending"){

        paypal
          .Buttons({
            createOrder: (data, actions) => {
              return actions.order.create({
                purchase_units: [
                  {
                    amount: {
                      currency_code: 'USD',
                      value: this.paymentStatus.totalPayment
                    }
                  }
                ]
              });
            },
            onApprove: async (data, actions) => {
              const order = await actions.order.capture();
              this.paidFor = true;
              console.log(order);
              console.log(order.create_time + " " + order.id);
              this.paymentStruct.timestamp = order.create_time;
              this.paymentStruct.id = order.id;
              let amount = order.purchase_units[0].amount.value;
              let amountInNumber = parseInt(amount.slice(0,amount.length-2));
              this.paymentStruct.amount = amountInNumber;


              this.paymentStruct.payeeName = order.payer.name.given_name + order.payer.name.surname
              // this.paymentStruct.payerName = order.payer.name.given_name + order.payer.name.surname;
              if(order.status == "COMPLETED"){
                this.paymentStruct.clientStatus = "Approved";
              }
              else{
                this.paymentStruct.clientStatus = "Rejected";
              }
              // this.paymentStruct.clientStatus = order.status;
              this.paymentStruct.clientFinanceComment = this.comment;
              this.paymentStruct.mode = "Online";
              this.paymentStruct.modeType = "PayPal";
              this.spinner.show();
              this.swSerivice.sendPaymentDetails(this.id,this.month,this.year,this.paymentStruct)
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
                  title: `Payment Done Successfully`
                })
                setTimeout(this.pageReload, 3000);
              },
                (error)=>{
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
                    icon: 'error',
                    title: `Error in Saving Data`
                  })
                }
              );
            },
            onError: err => {
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
                icon: 'error',
                title: `Error Occured. Try Again`
              })
            }
          })
          .render(this.paypalElement.nativeElement);
        }
        else{
          Swal.fire(
            {icon: 'info',
             title: 'You Responsed has been Saved.',}
          );
        }

    },
    (error)=>{
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
        icon: 'error',
        title: `Error Occured`
      })
    }
    )
  }

  rejectThePayment(){
    //Reject API
    this.spinner.show();
    this.swSerivice.rejectPaymentStatus(this.id,this.month,this.year,this.comment)
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
          title: `Rejection Message Send`
        })
        setTimeout(this.pageReload, 3000);
      },
      (error)=>{
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
          icon: 'error',
          title: `Error Occured Retry`
        })
      }
    );
  }


  offlinePayment(){
    this.paymentStruct.amount = this.paymentStatus.totalPayment;
    this.paymentStruct.clientStatus = "Approved";
    this.paymentStruct.clientFinanceComment = this.comment;
    this.paymentStruct.mode = "Offline";

    this.spinner.show();
    this.swSerivice.sendPaymentDetails(this.id,this.month,this.year,this.paymentStruct)
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
          title: `Payment Data has been Send`
        })
        setTimeout(this.pageReload, 3000);
      },
      (error)=>{
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
          icon: 'error',
          title: `Error Occured. Try Again`
        })
      }
    );
  }

  pageReload()
  {
    window.location.reload();
  }
}
