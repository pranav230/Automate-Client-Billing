import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ServiceClassService } from '../service-class.service';
import Swal from 'sweetalert2';
import { error } from 'selenium-webdriver';
// import { time } from 'node:console';

@Component({
  selector: 'app-temp-config',
  templateUrl: './temp-config.component.html',
  styleUrls: ['./temp-config.component.scss']
})
export class TempConfigComponent implements OnInit {

  http:HttpClient;
  temp:string = "100";
  configData:any;
  tempModel:any;
  isCheckBoxOpen = false;
  isDirectSend = false;
  isApprovedCheck = false;


  //For checkbox
  // checkBox =document.getElementById("myCheck") as HTMLInputElement;
  // text = document.getElementById("text");

  // checkBox =document.getElementById("myCheck") as HTMLInputElement;
  // text = document.getElementById("text");

  // console.log(this.checkBox : any);


  totalChanges :number = 0;

  isSaved:boolean = true;

  //current date....
  currDate;


  activatedRoute: ActivatedRoute;
  id:any;
  swService:ServiceClassService;
  details:any;
  month:string;
  year:string;

  constructor(swService: ServiceClassService,activatedRoute: ActivatedRoute,private spinner: NgxSpinnerService,http:HttpClient) {
    this.activatedRoute=activatedRoute;
    this.swService=swService;
    this.http = http;
   }

  ngOnInit(): void {
    // this.currDate = new Date().toISOString().slice(0, 10);

    this.spinner.show();
    this.activatedRoute.params.subscribe(
      (params) => {
        this.id=params.id;
        this.month=params.month;
        this.year=params.year;
      }
    );

    this.swService.getAllConfigData(this.id,this.month,this.year)
    .subscribe(
      (response) => {
          if(response){
            this.spinner.hide();
            this.configData = response;

            // this.configData = this.configData.employeeConfigList.filter(x =>x.endDate = this.timestampCalculation(x.endDate));
            // console.log(this.configData);
            if(this.configData.teamleadStatus != null){
              this.isApprovedCheck = true;
            }

            if(this.isApprovedCheck==true)
            this.configData.employeeConfigList = this.configData.employeeConfigList.filter(x =>{x.startDate = this.timestampCalculation(x.startDate);x.endDate = this.timestampCalculation(x.endDate); return true;});


          // console.log(this.configData);
          this.configData.totalPayment = 0;
          // console.log(this.configData.totalPayment);




          console.log(this.isApprovedCheck);


          if(this.isApprovedCheck != true){
            let text = document.getElementById("text");
            let checkBox =document.getElementById("myCheck") as HTMLInputElement;


            if(this.configData.project.gst > 0){
              // checkBox.checked = true;
              text.style.display = "block";
            }
            else{
              text.style.display = "none";
              console.log("E");
            }
          }
          else{
            if(this.configData.project.gst > 0){
            }
            else{
              this.configData.project.gst = 0;
            }
          }



          for(let entry of this.configData.employeeConfigList){
            entry['isEdit'] = false;
            entry['isDisabled'] = false;
            // entry['individualPay'] = 0;
            entry.individualPay = 0;
            this.calculationAccToTime(entry);
            // console.log(entry.isEdit);
          }
        }
      }
      );

      // $(document).ready(function(){
      //   $('#myBtn').click(function(){
      //     $('.toast').toast({delay: 2000});
      //     $('.toast').toast('show');
      //   });
      // });



      //calculation for hours billed
  }


  options = {
    autoClose: false,
    keepAfterRouteChange: false
  };

  runTotalPayment(){

    this.configData.totalPayment = 0;
    for(let poi of this.configData.employeeConfigList){
      this.configData.totalPayment += poi.individualPay;
    }

    let gstAmount = (this.configData.project.gst/100)*this.configData.totalPayment
    this.configData.totalPayment = this.configData.totalPayment + gstAmount;

    // console.log("GST Amout" + gstAmount + " "+ this.configData.totalPayment);


  }


  alert(){
    // var close = document.getElementsByClassName("toast");

    // $('#myDialog').on('click',function(e){
    //   e.preventDefault();

    //   bs4pop.notice('Notifiaction Message',{
    //     type : 'primary',
    //     position : 'top-right',
    //     appendType : 'append',
    //     closeBtn : false,
    //     className :''
    //   })
    // })

    // $('.toast').addClass('show');
    // close.toast({delay: 2000});
    Swal.fire('This is a simple and sweet alert')
  }


  calculationAccToTime(entry){

    //Total Days Exist...
    let stData = entry.startDate;
    let endDate = entry.endDate;
    let date1 = new Date(stData);
    let date2 = new Date(endDate);

    // console.log(date1+ " " +date2);

    let totalWeekends = this.countWeekendDays( new Date(stData), new Date(endDate) );
    // console.log(totalWeekends);
    let totalLeavesByEmoloyee = entry.leaves;
    // console.log(totalLeavesByEmoloyee);

    // To calculate the time difference of two dates
    // console.log(date1+ " " +date2);
    // var date3=new Date(stData);
    // var date4=new Date(endDate);

    let Difference_In_Time = date2.getTime() - date1.getTime();
    // console.log(Difference_In_Time);
    // To calculate the no. of days between two dates
    let Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
    // console.log(Difference_In_Days + " " + totalWeekends + " " + totalLeavesByEmoloyee);

    // console.log(stData + " " + endDate + " " + Difference_In_Days);

    let totalDaysLeft= Difference_In_Days - totalWeekends - totalLeavesByEmoloyee;
    // console.log(totalDaysLeft);
    entry.hoursBilled = totalDaysLeft * 8;
    // console.log(entry.hoursBilled + " " + entry.ratePerHour);

    // console.log(totalDaysLeft + "   " + entry.hoursBilled + "   "+ totalLeavesByEmoloyee);

    let pervSalary = entry.individualPay;
    entry.individualPay = (entry.hoursBilled)*(entry.ratePerHour);

    // console.log(entry.individualPay + "  " + pervSalary);
    this.configData.totalPayment +=  (entry.individualPay-pervSalary);
    // console.log(this.configData.totalPayment);


    this.runTotalPayment();

    // console.log(entry.individualPay);
  }



  myFunction(){

    console.log("A");

    let checkBox =document.getElementById("myCheck") as HTMLInputElement;
    let text = document.getElementById("text");

    console.log("B");

    if (checkBox.checked == true){
      text.style.display = "block";
    } else {
      text.style.display = "none";
    }
  }

  saveChanges(){
    this.isSaved = true;


    let tempVal = JSON.parse(JSON.stringify(this.configData));

    for(let entry of this.configData.employeeConfigList){
      delete entry.isEdit;
      delete entry.isDisabled;
      entry.leaves = parseInt(entry.leaves);
    }

    // console.log(this.configData);


    this.swService.saveChangeForConfigPage(this.month,this.year,this.configData)
    .subscribe((res) => {
      console.log(res);
    },
    (error) => {
      console.log(error);
    }
    )

    tempVal.employeeConfigList.filter(x => x.isEdit = false);
    tempVal.employeeConfigList.filter(x => x.isDisabled = false);

    this.configData = tempVal;

    if(this.isDirectSend == false){

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
      title: 'Data Successfully Saved'
    })

  }

  }



  sendForReview(){

    if(this.isSaved != true){

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
        icon: 'warning',
        title: 'Data not Saved'
      })


      return;
    }


    // this.swService.getAllConfigData(this.id,this.month,this.year)
    // .subscribe(
    //   (response) => {

    //       }
    //     }
    //   );

    this.spinner.show();

    this.isDirectSend = true;
    this.saveChanges();




    this.swService.sendEmailToManager(this.month,this.year,this.id)
    .subscribe((result) => {
        // console.log(result);

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
          title: 'Data Sent To Manager'
        })

        setTimeout(this.redirectPage, 3000);


      },
      (error)=>{
        console.log(error);
      }
    )





    //API.......

    // console.log("hh");

  }

  redirectPage(){
    window.location.href = '/billingDetails';
  }

  isFieldEdit(){
    this.isSaved = false;
  }

  editable(item){
    item.isEdit = true;

    item.isDisabled = true;

    this.isSaved = false;
  }


  onValueChange(data){
    // console.log(change);
    // this.configData.project.clientEmail = change;
    // console.log(this.configData.project.clientEmail);
    // console.log(data);
    // console.log(this.configData);
  }

  countWeekendDays( fromDate, toDate )
  {
    // var ndays = 1 + Math.round((d1.getTime()-d0.getTime())/(24*3600*1000));
    // var nsaturdays = Math.floor( (d0.getDay()+ndays) / 7 );
    // return 2*nsaturdays + (d0.getDay()==0 ? 0 : 1) - (d1.getDay()==6 ? 0 : 1);
    let localDate = fromDate;
    let weekendDayCount = 0;
    while(localDate < toDate){
        localDate.setDate(localDate.getDate() + 1);
        if(localDate.getDay() === 0){
            ++weekendDayCount ;
        }
    }
    return weekendDayCount ;

    // return 1;
  }

  timestampCalculation(data){
    const d = new Date(data);
    var month = d.toLocaleString('default', { month: 'short' });
    var day = d.getUTCDate();
    var year = d.getUTCFullYear();
    return day + " " + month + " " + year;
  }
}
