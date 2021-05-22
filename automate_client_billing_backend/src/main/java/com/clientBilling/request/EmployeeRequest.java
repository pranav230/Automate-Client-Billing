package com.clientBilling.request;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class EmployeeRequest {
    private String employeeID;
    private String teamLeadID;
    private String employeeName;
    private String designation;
    private String department;
    private String email;
    private String band;
    private String projectTitle;
    private String projectDesc;
    private String clientName;
    private LocalDate monthOFYear;
    private Integer leave;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double percent;
    private Integer poBalance;
    private String poNumber;
    private LocalDate poExpiry;
    private double gst;
    private Integer currentMonthLeaves;
    private Integer totalLeaves;
    private String clientEmail;

    public EmployeeRequest(String employeeID, String teamLeadID, String employeeName, String designation, String department, String email, String band, String projectTitle, String projectDesc, String clientName, LocalDate monthOFYear, Integer leave, LocalDate startDate, LocalDate endDate, Double percent, Integer poBalance, String poNumber, LocalDate poExpiry, double gst, Integer currentMonthLeaves, Integer totalLeaves, String clientEmail) {
        this.employeeID = employeeID;
        this.teamLeadID = teamLeadID;
        this.employeeName = employeeName;
        this.designation = designation;
        this.department = department;
        this.email = email;
        this.band = band;
        this.projectTitle = projectTitle;
        this.projectDesc = projectDesc;
        this.clientName = clientName;
        this.monthOFYear = monthOFYear;
        this.leave = leave;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.poBalance = poBalance;
        this.poNumber = poNumber;
        this.poExpiry = poExpiry;
        this.gst = gst;
        this.currentMonthLeaves = currentMonthLeaves;
        this.totalLeaves = totalLeaves;
        this.clientEmail = clientEmail;
    }
}
