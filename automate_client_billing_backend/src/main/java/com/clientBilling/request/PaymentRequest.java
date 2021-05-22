package com.clientBilling.request;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class PaymentRequest {
    private Integer projectID;
    private Integer clientTotalPayment;
    private String teamLeadFeedback;
    private String teamLeadStatus;
    private String teamManagerComment;
    private String clientFinanceComment;
    private String financeManagerComment;
    private String financeClientComment;
    private String clientStatus;
    private Integer clientReminder;
    private Integer teamLeadReminder;

    public PaymentRequest(Integer projectID, Integer clientTotalPayment, String teamLeadFeedback, String teamLeadStatus, String teamManagerComment, String clientFinanceComment, String financeManagerComment, String financeClientComment, String clientStatus, Integer clientReminder, Integer teamLeadReminder) {
        this.projectID = projectID;
        this.clientTotalPayment = clientTotalPayment;
        this.teamLeadFeedback = teamLeadFeedback;
        this.teamLeadStatus = teamLeadStatus;
        this.teamManagerComment = teamManagerComment;
        this.clientFinanceComment = clientFinanceComment;
        this.financeManagerComment = financeManagerComment;
        this.financeClientComment = financeClientComment;
        this.clientStatus = clientStatus;
        this.clientReminder = clientReminder;
        this.teamLeadReminder = teamLeadReminder;
    }
}
