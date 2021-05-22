import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-lead-response-rejected',
  templateUrl: './lead-response-rejected.component.html',
  styleUrls: ['./lead-response-rejected.component.scss']
})
export class LeadResponseRejectedComponent implements OnInit {

  @Input() info;
  constructor() { }

  ngOnInit(): void {
  }

}
