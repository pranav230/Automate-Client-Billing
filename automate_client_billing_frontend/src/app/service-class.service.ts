import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BillingData } from './billingModel';
import { Home } from './homeModel';
import { BillingModelList } from './billingModelList';

import { SocialAuthService } from 'angularx-social-login';
import { SocialUser } from 'angularx-social-login';  /* Import for storing User Information */
import {
  GoogleLoginProvider
} from 'angularx-social-login';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { ClientEmailList } from './clientEmailModel';


@Injectable({
  providedIn: 'root'
})
export class ServiceClassService {

  usercred:any;
  user: SocialUser;
  projects: Home[];
  employeeDetail : BillingModelList;
  incom:BillingData[];
  projectDetails : any;
  teamResponseRecord: any;
  teamDetails:any;
  clientPaymentRecord:any;
  userDesignation:any;
  constructor(private http:HttpClient , private authService: SocialAuthService, private router:Router){
  }


  getAllClientData(month,year){
    var clientDetailUrl='https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/client'+'/'+month+'/'+year;
    return this.http.get(clientDetailUrl);
  }

  getAllLeadData(month,year){
    var leadDetailUrl='https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/response'+'/'+month+'/'+year;
    return this.http.get(leadDetailUrl);
  }

  getAllDetails(id,month,year){
    return this.http.get('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/details/'+id+'/'+month+'/'+year);
  }

  getAllData(month,year){
    var billDetailUrl='https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/billdetails'+'/'+month+'/'+year;
    return this.http.get(billDetailUrl);
  }

  getHome(month,year) {
    var homeUrl='https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/home'+'/'+month+'/'+year;
    return this.http.get(homeUrl);
  }

  getNewNotification() {
    var newNotificationUrl='https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/newNotification';
    return this.http.get(newNotificationUrl);
  }

  getQuotes() {
    return this.http.get('https://type.fit/api/quotes');
  }

  sendClientPayment(clientEmailList,month,year){
    return this.http.post('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/sendClientPaymentMail/'+month+'/'+year, clientEmailList);
  }

  saveChangeForConfigPage(month,year,configData){
    console.log(configData);
    return this.http.patch('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/config/'+month+'/'+year,configData,{responseType : 'text'});

  }

  sendUpdateToFinance(month,year,proID){
    proID=parseInt(proID);
    return this.http.patch('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/updateManager/'+month+'/'+year,proID,{responseType : 'text'});
  }

  sendEmailToManager(month,year,id){
    // month = parseInt(month);
    // year = parseInt(year);
    id = parseInt(id);
    console.log(typeof id);
    return this.http.post('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/sendLeadApprovalMail/'+month+'/'+year,id,{responseType : 'text'});
  }

  sendReminderMailToLead(id,month,year){
    id = parseInt(id);
    return this.http.post('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/sendLeadReminder/'+month+'/'+year,id,{responseType : 'text'});
  }

  sendPaymentDetails(id,month,year,paymentStruct){
    return this.http.post('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/addPayment/'+id+'/'+month+'/'+year,paymentStruct,{responseType : 'text'});
  }


  sendEmailToClientForReminder(month,year,id){
    id = parseInt(id);
    console.log(id + " " + month + " " +year);
    return this.http.post('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/sendClientReminder/'+month+'/'+year,id,{responseType : 'text'});
  }

  getTotalPaymentStatus(id,month,year){
    var newPaymentStatus='https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/totalPayment/'+id+'/'+month+'/'+year;
    return  this.http.get(newPaymentStatus);
  }

  rejectPaymentStatus(id,month,year,comment){
    var email = new ClientEmailList();
    email.projectId = id;
    email.toClientComment = comment;
    return this.http.patch('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/updateRejectClientPayment/'+month+'/'+year,email,{responseType : 'text'});
  }

  // https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/sendClientReminder/5/2021

  /* Use To Keep store the login Credential in local storage for prevent from page refresh */
  setSettings(data: any) {
    localStorage.setItem(this.usercred, JSON.stringify(data));
  }


  getUserSettings() {
    let data = localStorage.getItem(this.usercred);
    return JSON.parse(data);
  }

  clearUserSettings() {
    localStorage.removeItem(this.usercred);
  }

  cleanAll() {
    localStorage.clear()
  }
  /* Use To Keep store the login Credential in local storage for prevent from page refresh */

  signInWithGoogle(): void {
    this.authService.authState.subscribe(user => {
      if(user)
      {
        console.log(this.authService);
        this.verifyUser(user.email)
        .subscribe((response)=>{
          if(response==null)
          {
            this.signOut();
            this. clearUserSettings();
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
              title: `Not an Authenticated User`
            })
          }
          else{
            this.userDesignation=response;
            this.router.navigate(["/home"]);
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
              title: `Successfully Login`
            })
          }
        })

      }
      this.user = user;

      /* Use to Store the details in Local Storage */
      this.setSettings(this.user);
      console.log(this.user);
    });
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

  signOut(): void {
    this.clearUserSettings();
    this.authService.signOut(true);
    this.router.navigate([""]);
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
      title: `Successfully LogOut`
    })
  }

  updateNotificationTimestamp(){
    this.http.post('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/addTimestamp',1,{responseType: 'text'})
    .subscribe(
      res => {
        console.log(res);
      });
  }

  getAllNotification(){
    var allNotificationUrl='https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/viewAllNotification';
    return this.http.get(allNotificationUrl);
  }

  addNewMember(email,designation){

    var emailId= new Email;
    emailId.email=email;
    emailId.designation=designation;

    console.log(emailId);
    return this.http.post('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/addFinanceEmail',emailId,{responseType: 'text'});
  }

  getAllConfigData(id,month,year){
    return this.http.get('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/config/'+id+'/'+month+'/'+year);
  }

  getTransactionSummary(id,month,year) {
    return this.http.get('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/transactionSummary/'+id+'/'+month+'/'+year);
  }

  getRejectionComment(id,month,year) {
    return this.http.get('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/rejectionMessage/'+id+'/'+month+'/'+year,{responseType : 'text'});
  }

  verifyUser(email){
    return this.http.get('https://automateclientbilling-dot-hu18-groupa-angular.et.r.appspot.com/verifyUser/'+email);
  }
}

export class Email{
  public email="";
  public designation="";
}
