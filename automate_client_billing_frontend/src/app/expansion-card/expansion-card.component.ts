import { Component, Input, OnInit } from '@angular/core';
import {MatAccordion} from '@angular/material/expansion';
import {MatDialog} from '@angular/material/dialog';
import { ExpansionPopCardInfoComponent } from '../expansion-pop-card-info/expansion-pop-card-info.component';
import {ServiceClassService} from '../service-class.service';
// import { ViewEncapsulation } from '@angular/core';


@Component({
  selector: 'app-expansion-card',
  templateUrl: './expansion-card.component.html',
  styleUrls: ['./expansion-card.component.scss']
  // encapsulation: ViewEncapsulation.None
})
export class ExpansionCardComponent implements OnInit {

  @Input() projectData: any;
  @Input() currMMYY:any;
  projectEmployeeInfo = [];
  swService : ServiceClassService;
  panelOpenState = false;
  disableGenerateReport = false;




  constructor(public dialog: MatDialog,swService : ServiceClassService) {
    this.swService = swService;
   }

  openDialog() {
    // this.swService.projectDetails = this.projectData;
    // const dialogRef = this.dialog.open(ExpansionPopCardInfoComponent);
    console.log(this.currMMYY);
    console.log(this.projectData);
  }

  ngOnInit(): void {
    if(this.projectData.leadStatus != null){
      this.disableGenerateReport = true;
    }
    else{
      this.disableGenerateReport = false;
    }
  }

}
