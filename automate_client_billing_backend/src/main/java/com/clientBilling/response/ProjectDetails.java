package com.clientBilling.response;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDetails {
    private String clientName;
    private String projectName;
    private String teamLeadID;
    private String teamLeadName;
    private String teamLeadStatus;
    private String clientPaymentStatus;
    private Integer clientTotalPayment;
    private Integer teamLeadReminder;
    private Integer clientReminder;
    private String teamLeadFeedback;
    private String clientFeedback;
    private String financeToClientComment;
    private Integer totalEmployees;
    private String modeOfPayment;
    private List<EmployeeBilldetailsResponse> employees;

    public ProjectDetails(String clientName, String projectName, String teamLeadID, String teamLeadName, String teamLeadStatus, String clientPaymentStatus, Integer clientTotalPayment, Integer teamLeadReminder, Integer clientReminder, String teamLeadFeedback, String clientFeedback, String financeToClientComment, Integer totalEmployees, String modeOfPayment, List<EmployeeBilldetailsResponse> employees) {
        this.clientName = clientName;
        this.projectName = projectName;
        this.teamLeadID = teamLeadID;
        this.teamLeadName = teamLeadName;
        this.teamLeadStatus = teamLeadStatus;
        this.clientPaymentStatus = clientPaymentStatus;
        this.clientTotalPayment = clientTotalPayment;
        this.teamLeadReminder = teamLeadReminder;
        this.clientReminder = clientReminder;
        this.teamLeadFeedback = teamLeadFeedback;
        this.clientFeedback = clientFeedback;
        this.financeToClientComment = financeToClientComment;
        this.totalEmployees = totalEmployees;
        this.modeOfPayment = modeOfPayment;
        this.employees = employees;
    }
}
