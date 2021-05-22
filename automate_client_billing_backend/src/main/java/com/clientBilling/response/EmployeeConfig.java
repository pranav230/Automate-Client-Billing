package com.clientBilling.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeConfig {
    private LocalDate startDate;
    private LocalDate endDate;
    private String empID;
    private String empName;
    private String designation;
    private Integer leaves;
    private Integer currentMonthLeaves;
    private Integer leavesTotal;
    private Integer ratePerHour;
    private Integer hoursBilled;
    private Integer serialID;
    private Integer individualPay;

    public EmployeeConfig(LocalDate startDate, LocalDate endDate, String empID, String empName, String designation, Integer leaves, Integer currentMonthLeaves, Integer leavesTotal, Integer ratePerHour, Integer hoursBilled, Integer serialID, Integer individualPay) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.empID = empID;
        this.empName = empName;
        this.designation = designation;
        this.leaves = leaves;
        this.currentMonthLeaves = currentMonthLeaves;
        this.leavesTotal = leavesTotal;
        this.ratePerHour = ratePerHour;
        this.hoursBilled = hoursBilled;
        this.serialID = serialID;
        this.individualPay = individualPay;
    }
}
