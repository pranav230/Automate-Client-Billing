package com.clientBilling.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ClientPaymentResponse {
    private Integer requestId;
    private Integer totalPayment;
    private String feedback;
    private String clientName;
    private String projectName;
    private Integer projectID;
    private Integer clientReminder;
    private Timestamp timestamp;

    public ClientPaymentResponse(Integer requestId, Integer totalPayment, String feedback, String clientName, String projectName, Integer projectID, Integer clientReminder, Timestamp timestamp) {
        this.requestId = requestId;
        this.totalPayment = totalPayment;
        this.feedback = feedback;
        this.clientName = clientName;
        this.projectName = projectName;
        this.projectID = projectID;
        this.clientReminder = clientReminder;
        this.timestamp = timestamp;
    }
}
