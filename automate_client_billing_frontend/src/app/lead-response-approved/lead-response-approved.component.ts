import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { ServiceClassService } from '../service-class.service';

@Component({
  selector: 'app-lead-response-approved',
  templateUrl: './lead-response-approved.component.html',
  styleUrls: ['./lead-response-approved.component.scss']
})
export class LeadResponseApprovedComponent implements OnInit {

  @Input() info;
  @Input() month;
  @Input() year;
  constructor(private https:HttpClient, private swservice:ServiceClassService) { }

  errorMessage;

  ngOnInit(): void {

  }

  sendEmail(){
    this.swservice.sendClientPayment(this.info.projectID,this.month,this.year);
  }
}
