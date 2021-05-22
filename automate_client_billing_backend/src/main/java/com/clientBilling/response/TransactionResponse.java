package com.clientBilling.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionResponse {
    public String id;
    public Integer amount;
    public String payeeName;
    public Timestamp timestamp;
    public String mode;
    public String modeType;
    public String clientFinanceComment;

    public TransactionResponse(String id, Integer amount, String payeeName, Timestamp timestamp, String mode, String modeType, String clientFinanceComment) {
        this.id = id;
        this.amount = amount;
        this.payeeName = payeeName;
        this.timestamp = timestamp;
        this.mode = mode;
        this.modeType = modeType;
        this.clientFinanceComment = clientFinanceComment;
    }
}
