package com.clientBilling.controller;

import com.clientBilling.entity.Band;
import com.clientBilling.entity.FinanceEmails;
import com.clientBilling.entity.Notification;
import com.clientBilling.request.EmployeeRequest;
import com.clientBilling.request.PaymentRequest;
import com.clientBilling.response.*;
import com.clientBilling.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping("/")
    public String index() {
        return "Welcome To Hashedin University";
    }

    @GetMapping("/billdetails/{month}/{year}")
    public List<BilldetailsResponse> billdetail(@PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        return employeeService.getBillDetails(month, year);
    }

    @GetMapping("/response/{month}/{year}")
    public List<TeamLeadResponse> leadResponse(@PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        return employeeService.leadResponse(month, year);
    }

    @GetMapping("/client/{month}/{year}")
    public List<ClientPaymentResponse> clientResponse(@PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        return employeeService.clientPaymentList(month, year);
    }

    @GetMapping("/details/{code}/{month}/{year}")
    public ProjectDetails projectDetails(@PathVariable("code") Integer code, @PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        return employeeService.projectDetails(code, month, year);
    }

    @GetMapping("/home/{month}/{year}")
    public List<HomePageResponse> homePagedetail(@PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        return employeeService.getHomePageDetails(month, year);
    }

    @GetMapping("/viewAllNotification")
    public NotificationResponse getAllNotifications() {
        return employeeService.getAllNotifications();
    }

    @GetMapping("/newNotification")
    public List<Notification> getNotification() {
        return employeeService.getNotifications();
    }

    @GetMapping("/config/{code}/{month}/{year}")
    public ConfigResponse configDetails(@PathVariable("code") Integer code, @PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        return employeeService.configDetails(code, month, year);
    }

    @GetMapping("/totalPayment/{code}/{month}/{year}")
    public PaymentResponse getTotalPayment(@PathVariable("code") Integer code, @PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        return employeeService.getTotalPayment(code, month, year);
    }

    @GetMapping("/transactionSummary/{code}/{month}/{year}")
    public TransactionResponse getTransaction(@PathVariable("code") Integer code, @PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        return employeeService.getTransactionSummary(code, month, year);
    }

    @GetMapping("/rejectionMessage/{code}/{month}/{year}")
    public String getRejectionMessage(@PathVariable("code") Integer code, @PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        return employeeService.getRejectionMessage(code,month,year);
    }

    @GetMapping("/verifyUser/{email}")
    public FinanceEmails verifyUser(@PathVariable("email") String email){
        return employeeService.verifyUser(email);
    }
}