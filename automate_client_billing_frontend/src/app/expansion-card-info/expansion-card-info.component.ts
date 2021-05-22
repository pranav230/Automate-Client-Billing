import { Component, Input, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { EmployeeInfoPopUpComponent } from '../employee-info-pop-up/employee-info-pop-up.component';
import { ServiceClassService } from '../service-class.service';

@Component({
  selector: 'app-expansion-card-info',
  templateUrl: './expansion-card-info.component.html',
  styleUrls: ['./expansion-card-info.component.scss']
})
export class ExpansionCardInfoComponent implements OnInit {

  @Input() employeeData: any;
  swService : ServiceClassService;

  constructor(public dialog: MatDialog,swService : ServiceClassService) {
    this.swService = swService;
   }

  openDialog() {
    this.swService.employeeDetail = this.employeeData;
    console.log(this.swService.incom);
    const dialogRef = this.dialog.open(EmployeeInfoPopUpComponent);
  }

  ngOnInit(): void {
  }

}
