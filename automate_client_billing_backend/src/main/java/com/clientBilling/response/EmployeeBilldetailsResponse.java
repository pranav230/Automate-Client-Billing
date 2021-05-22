package com.clientBilling.response;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

@Data
public class EmployeeBilldetailsResponse {
    private String employeeId;
    private String employeeName;
    private String designation;
    private Integer leaves;
    private Integer hoursBilled;
    private Integer ratePerHour;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer individualPay;

    public EmployeeBilldetailsResponse(String employeeId, String employeeName, String designation, Integer leaves, Integer hoursBilled, Integer ratePerHour, LocalDate startDate, LocalDate endDate, Integer individualPay) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.designation = designation;
        this.leaves = leaves;
        this.hoursBilled = hoursBilled;
        this.ratePerHour = ratePerHour;
        this.startDate = startDate;
        this.endDate = endDate;
        this.individualPay = individualPay;
    }
}
