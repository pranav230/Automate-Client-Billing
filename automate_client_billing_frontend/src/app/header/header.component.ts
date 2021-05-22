import { Component, OnInit } from '@angular/core';

/* Use For Page Routing */
import { Router } from '@angular/router';
/* Use For login Authentication Sign Out */
import { SocialAuthService } from 'angularx-social-login';
import { ServiceClassService } from '../service-class.service';
import { SocialUser } from 'angularx-social-login';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  userCredentials:SocialUser;
  newNotification;
  sizeOfNotification;
  currDate=new Date();
  userdesignation:any;
  oneday = 1000 * 60 * 60 * 24;

  constructor(private authService: SocialAuthService ,  private router:Router ,private swservice: ServiceClassService) { }

  ngOnInit(): void {
    this.userCredentials=this.swservice.getUserSettings();
    this.swservice.verifyUser(this.userCredentials.email)
    .subscribe(
      (response)=>{
        if(response!=null){
          this.userdesignation=response;
        }
      }
    )
    // console.log(this.userCredentials);
    this.userdesignation=this.swservice.userDesignation;
    // console.log(this.userdesignation);
    this.swservice.getNewNotification()
    .subscribe(
      (response) => {
          if(response){
            this.newNotification=response;
            // console.log(this.newNotification);
            this.sizeOfNotification=this.newNotification.length;

            for (let entry of this.newNotification)
            {
              entry.name=(entry.name)[0].toUpperCase()+(entry.name).slice(1);
              const d1 = new Date( entry.timestamp);
              var d = new Date( d1.getUTCFullYear(), d1.getUTCMonth(), d1.getUTCDate(), d1.getUTCHours(), d1.getUTCMinutes(), d1.getUTCSeconds() );
              var reminder=Math.abs(this.currDate.getTime()-d.getTime())/3600000;
              // console.log(d,reminder);
              if(reminder>24)
              {
                var month = d.toLocaleString('default', { month: 'short' });
                var day = d.getUTCDate();
                var year = d.getUTCFullYear();
                entry.timestamp   = day + "-" + month + "-" + year;
              }
              else
              {
                if(reminder<1)
                {
                  reminder=reminder*60;
                  if(reminder<0.6)
                  {
                    reminder=reminder*60;
                    entry.timestamp=Math.round(reminder) + " seconds ago";
                  }
                  else
                  {
                    entry.timestamp=Math.round(reminder) + " minutes ago";
                  }
                }
                else
                {
                  entry.timestamp=Math.round(reminder) + " hours ago";
                }
              }
              // console.log(entry.timestamp);
            }
          }
        }
      );
  }

  signOut(): void {
    this.swservice.signOut();
  }

  updateNotification():void{
    this.swservice.updateNotificationTimestamp();
    this.sizeOfNotification=0;
  }

  async addMember(){
    const { value: email } = await Swal.fire({
      title: 'New Member Email Address',
      input: 'email',
      inputLabel: 'Your email address',
      inputPlaceholder: 'Enter your email address',
      showClass: {
        popup: 'animated fadeInDown faster',
        icon: 'animated heartBeat delay-1s'
      },
      hideClass: {
        popup: 'animated fadeOutUp faster',
      }
    })

    if(email) {
      const { value: designation } = await Swal.fire({
        title: 'Select Employee Designation',
        input: 'select',
        inputOptions: {
          Manager:'Manager',
          Employee:'Employee'
        },
        inputPlaceholder: 'Select Employee Designation',
        showClass: {
          popup: 'animated fadeInDown faster',
          icon: 'animated heartBeat delay-1s'
        },
        hideClass: {
          popup: 'animated fadeOutUp faster',
        }
      })

      console.log(email,designation);
      this.swservice.addNewMember(email,designation)
      .subscribe(
        (response) => {
            if(response){
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
                title: `Email Added Successfully: ${email}`
              })
            }
          });
    }
  }
}
