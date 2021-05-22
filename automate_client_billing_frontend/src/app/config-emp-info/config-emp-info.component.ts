import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-config-emp-info',
  templateUrl: './config-emp-info.component.html',
  styleUrls: ['./config-emp-info.component.scss']
})
export class ConfigEmpInfoComponent implements OnInit {

  constructor() { }

  temp:string = "100";
  isEdit : boolean = false;

  ngOnInit(): void {
  }

  editable(){
    // document.getElementById("leaves-taken").removeAttribute("readonly");
    // document.getElementById("cur-month").removeAttribute("readonly");
    // document.getElementById("tot-month").removeAttribute("readonly");
    // document.getElementById("rate").removeAttribute("readonly");

    // var fields = document.getElementsByClassName('leaves-taken');


    // console.log(fields.length);

    // for(var x=0;x<fields.length;x++) {
    //     fields[x].removeAttribute("readonly");
    // }

    this.isEdit = true;

  }

}
