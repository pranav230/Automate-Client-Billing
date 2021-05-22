package com.clientBilling.request;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
public class PaymentModeRequest {
    private String id;
    private Integer amount;
    private String payeeName;
    private String mode;
    private String modeType;
    private Timestamp timestamp;
    private String clientStatus;
    private String clientFinanceComment;

    public PaymentModeRequest(String id, Integer amount, String payeeName, String mode, String modeType, Timestamp timestamp, String clientStatus, String clientFinanceComment) {
        this.id = id;
        this.amount = amount;
        this.payeeName = payeeName;
        this.mode = mode;
        this.modeType = modeType;
        this.timestamp = timestamp;
        this.clientStatus = clientStatus;
        this.clientFinanceComment = clientFinanceComment;
    }
}
