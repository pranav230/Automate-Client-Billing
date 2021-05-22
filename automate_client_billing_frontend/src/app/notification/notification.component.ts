import { Component, OnInit } from '@angular/core';
import { ServiceClassService } from '../service-class.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {

  allNotification:any;
  currDate=new Date();
  oneday = 1000 * 60 * 60 * 24;
  constructor(private swservice:ServiceClassService) { }

  ngOnInit(): void {
    this.swservice.getAllNotification()
    .subscribe(
      (response) => {
          if(response){
            this.allNotification=response;
            console.log(this.allNotification);

            for (let entry of this.allNotification.notificationList)
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
                    entry.timestamp="Just now";
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

}
