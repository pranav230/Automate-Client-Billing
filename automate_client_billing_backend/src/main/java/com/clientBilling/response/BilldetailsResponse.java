package com.clientBilling.response;

import lombok.Data;

import java.util.List;

@Data
public class BilldetailsResponse {
    private Integer projectId;
    private String projectName;
    private String clientName;
    private Integer totalEmployees;
    private Integer totalPayment;
    private String leadStatus;
    private List<EmployeeBilldetailsResponse> employeeList;

    public BilldetailsResponse(Integer projectId, String projectName, String clientName, Integer totalEmployees, Integer totalPayment, String leadStatus, List<EmployeeBilldetailsResponse> employeeList) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.clientName = clientName;
        this.totalEmployees = totalEmployees;
        this.totalPayment = totalPayment;
        this.leadStatus = leadStatus;
        this.employeeList = employeeList;
    }
}
