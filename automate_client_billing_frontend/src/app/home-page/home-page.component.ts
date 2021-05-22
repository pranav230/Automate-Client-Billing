import { Message } from '@angular/compiler/src/i18n/i18n_ast';
import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ServiceClassService } from '../service-class.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  user:any;
  current= new Date();
  day_night;
  message:string;
  randomNumber:any;
  quotes:any=[];
  timer;
  constructor(private swService: ServiceClassService, private spinner: NgxSpinnerService) {
    this.user=this.swService.getUserSettings();
   }

  ngOnInit(): void {
    this.spinner.show();
    this.swService.getQuotes()
    .subscribe(
    (response) => {
        if(response){
          this.quotes=response;
          this.randomNumber=Math.floor((Math.random() * this.quotes.length) + 1);
          this.spinner.hide();
        }
      }
    );

    this.day_night=this.current.getHours()
    if(this.day_night<=12 && this.day_night>=5)
    {
      this.message="Good Morning";
    }
    else if(this.day_night>=18 || this.day_night<=5)
    {
      this.message="Good Evening";
    }
    else
    {
      this.message="Good Afternoon";
    }

    if(this.day_night<=10 && this.day_night>=5)
    {
      this.timer=1;
    }
    else if(this.day_night>=10 && this.day_night<=17)
    {
      this.timer=2;
    }
    else if(this.day_night>=17 && this.day_night<=19)
    {
      this.timer=3;
    }
    else
    {
      this.timer=4;
    }
  }
}
