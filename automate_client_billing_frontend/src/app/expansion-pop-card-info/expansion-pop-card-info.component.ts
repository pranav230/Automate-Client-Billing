import { Component, OnInit } from '@angular/core';
import { ServiceClassService } from '../service-class.service';
// import { ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-expansion-pop-card-info',
  templateUrl: './expansion-pop-card-info.component.html',
  styleUrls: ['./expansion-pop-card-info.component.scss']
  // encapsulation: ViewEncapsulation.None
})
export class ExpansionPopCardInfoComponent implements OnInit {

  projectDetailPopUp : any;
  swService : ServiceClassService;
  constructor(swService : ServiceClassService) {
    this.swService = swService;
   }

  ngOnInit(): void {
    this.projectDetailPopUp = this.swService.projectDetails;
  }

}
