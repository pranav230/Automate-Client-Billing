export class BillingData {

  projectId : string ="";
  projectName : string ="";
  clientName : string ="";
  totalEmployees:number=0;
  totalPayment:number=0;
  leadStatus:string;

  employeeList : [{employeeId:string,employeeName : string,designation :string,band:string,salary:number ,
  leaves:number ,
  actualSalary:number,
  percentage:number
  }];

}
