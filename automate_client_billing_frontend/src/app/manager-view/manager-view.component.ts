import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2';
import { ServiceClassService } from '../service-class.service';
@Component({
  selector: 'app-manager-view',
  templateUrl: './manager-view.component.html',
  styleUrls: ['./manager-view.component.scss']
})
export class ManagerViewComponent implements OnInit {

  http:HttpClient;
  temp:string = "100";
  configData:any;
  tempModel:any;

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
  status;

  constructor(swService: ServiceClassService,activatedRoute: ActivatedRoute,private spinner: NgxSpinnerService,http:HttpClient) {
    this.activatedRoute=activatedRoute;
    this.swService=swService;
    this.http = http;
   }

  ngOnInit(): void {
    this.currDate = new Date().toISOString().slice(0, 10);

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
          }

          this.configData = response;
          console.log(this.configData);
          this.configData.totalPayment = 0;
          console.log(this.configData.totalPayment);
          // let text = document.getElementById("text");
          // let checkBox =document.getElementById("myCheck") as HTMLInputElement;

          // if(this.configData.project.gst > 0){
          //   checkBox.checked = true;
          //   text.style.display = "block";
          // }
          // else{
          //   text.style.display = "none";
          // }

          if(this.configData.teamleadStatus!='Pending')
            {
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
                title: `Data successfully Approved and sent to Finance Team`,
              })
            }
          else{
            for(let entry of this.configData.employeeConfigList){
              entry['isEdit'] = false;
              entry.individualPay=0;
              this.calculationAccToTime(entry);
              console.log(entry.isEdit);
            }
        }}
      );

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
  }


  alert(){
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
    console.log(totalLeavesByEmoloyee);

    // To calculate the time difference of two dates
    // console.log(date1+ " " +date2);
    // var date3=new Date(stData);
    // var date4=new Date(endDate);

    let Difference_In_Time = date2.getTime() - date1.getTime();
    console.log(Difference_In_Time);
    // To calculate the no. of days between two dates
    let Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
    console.log(Difference_In_Days + " " + totalWeekends + " " + totalLeavesByEmoloyee);

    // console.log(stData + " " + endDate + " " + Difference_In_Days);

    let totalDaysLeft= Difference_In_Days - totalWeekends - totalLeavesByEmoloyee;
    console.log(totalDaysLeft);
    entry.hoursBilled = totalDaysLeft * 8;
    console.log(entry.hoursBilled + " " + entry.ratePerHour);

    // console.log(totalDaysLeft + "   " + entry.hoursBilled + "   "+ totalLeavesByEmoloyee);

    let pervSalary = entry.individualPay;
    entry.individualPay = (entry.hoursBilled)*(entry.ratePerHour);

    console.log(entry.individualPay + "  " + pervSalary);
    this.configData.totalPayment +=  (entry.individualPay-pervSalary);
    console.log(this.configData.totalPayment);


    // this.runTotalPayment();

    // console.log(entry.individualPay);
  }



  // myFunction(){
  //   var checkBox =document.getElementById("myCheck") as HTMLInputElement;

  //   var text = document.getElementById("text");
  //   if (checkBox.checked == true){
  //     text.style.display = "block";
  //   } else {
  //     text.style.display = "none";
  //   }
  // }

  saveChanges(){
    // console.log(this.configData);
    this.isSaved = true;



    //API for backend.....
    let tempVal = JSON.parse(JSON.stringify(this.configData));


    //remove arr.isEdit...
    for(let entry of this.configData.employeeConfigList){
      delete entry.isEdit;
      entry.leaves = parseInt(entry.leaves);
    }

    console.log(this.configData);

    this.swService.saveChangeForConfigPage(this.month,this.year,this.configData)
    .subscribe((res) => {
      console.log(res);
    },
    (error) => {
      console.log(error);
    }
    )


    tempVal.employeeConfigList.filter(x => x.isEdit = false);
    this.configData = tempVal;
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
      title: 'Changes successfully made.'
    })

  }



  sendForReview(){

    if(this.isSaved != true){
      Swal.fire({
        position: 'top-right',
        width:'300px',
        icon: 'warning',
        title: 'You forgot to save',
        showConfirmButton: false,
        timer : 1500
      })
      return;
    }
    this.spinner.show();
    this.swService.sendUpdateToFinance(this.month,this.year,this.id)
    .subscribe((result) => {
      console.log(result);

      this.spinner.hide();

      // window.location.reload();
      this.ngOnInit();

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
        title: 'Error occured.'
      })
    }
  )



;

  }

  // redirectPage(){
  //   window.location.href = '/billingDetails';
  // }

  isFieldEdit(){
    this.isSaved = false;
  }

  editable(item){
    item.isEdit = true;
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

}
