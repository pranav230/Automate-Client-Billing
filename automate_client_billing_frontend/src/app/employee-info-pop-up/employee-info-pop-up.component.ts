import { Component, OnInit } from '@angular/core';
import { ServiceClassService } from '../service-class.service';
import {MatDialog} from '@angular/material/dialog';
// import { ViewEncapsulation } from '@angular/core';
import { BillingModelList } from '../billingModelList';

@Component({
  selector: 'app-employee-info-pop-up',
  templateUrl: './employee-info-pop-up.component.html',
  styleUrls: ['./employee-info-pop-up.component.scss']
  // encapsulation: ViewEncapsulation.None
})
export class EmployeeInfoPopUpComponent implements OnInit {

  swService : ServiceClassService;

  employeeDetails : BillingModelList;

  constructor(swService : ServiceClassService) {
    this.swService = swService;
   }

  ngOnInit(): void {
    this.employeeDetails = this.swService.employeeDetail;
  }

}
