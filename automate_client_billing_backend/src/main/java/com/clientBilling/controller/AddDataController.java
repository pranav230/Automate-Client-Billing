package com.clientBilling.controller;

import com.clientBilling.entity.Band;
import com.clientBilling.entity.FinanceEmails;
import com.clientBilling.entity.Payment;
import com.clientBilling.request.EmployeeRequest;
import com.clientBilling.request.NotificationRequest;
import com.clientBilling.request.PaymentModeRequest;
import com.clientBilling.request.PaymentRequest;
import com.clientBilling.service.AddingDataService;
import com.clientBilling.service.UpdatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddDataController {

    @Autowired
    private AddingDataService addingDataService;

    @Autowired
    private UpdatingService updatingService;

    public void EmployeeController(AddingDataService addingDataService, UpdatingService updatingService){
        this.addingDataService=addingDataService;
        this.updatingService = updatingService;
    }

    @PostMapping("/addEmployee")
    public String create(@RequestBody EmployeeRequest employeeDetail) {
        return addingDataService.addData(employeeDetail);
    }

    @PostMapping("/addBand")
    public String addBand(@RequestBody Band band){
        return addingDataService.addBand(band);
    }

    @PostMapping("/addClientPayment")
    public String createPayment(@RequestBody PaymentRequest paymentReuqest){
        return addingDataService.createPayment(paymentReuqest);
    }

    @PostMapping("/addFinanceEmail")
    public String addFinanceEmail(@RequestBody FinanceEmails financeEmails){
        return addingDataService.addFinanceEmail(financeEmails);
    }

    @PostMapping("/addNotification")
    public String addNotification(@RequestBody NotificationRequest notificationRequest){
        return addingDataService.addNotification(notificationRequest);
    }

    @PostMapping("/addTimestamp")
    public String addTimestamp(@RequestBody Integer body){
        return addingDataService.addTimeStamp();
    }

    @PostMapping("/addPayment/{code}/{month}/{year}")
    public String addPayment(@RequestBody PaymentModeRequest paymentMode, @PathVariable("code") Integer code, @PathVariable("month") Integer month, @PathVariable("year") Integer year){
        updatingService.updateClientStatus(code,month,year,"Approved");
        return addingDataService.addPayment(code,month,year,paymentMode);
    }
}
