package com.clientBilling.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Table
@Entity
@Data
public class Payment {
    @Id
    @Column(name = "transactionID")
    private String id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "payee_name")
    private String payeeName;

    @Column(name = "mode")
    private String mode;

    @Column(name = "mode_type")
    private String modeType;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @OneToOne
    @JoinColumn(name = "request")
    private Request request;

    public Payment(String id, Integer amount, String payeeName, String mode, String modeType, Timestamp timestamp, Request request) {
        this.id = id;
        this.amount = amount;
        this.payeeName = payeeName;
        this.mode = mode;
        this.modeType = modeType;
        this.timestamp = timestamp;
        this.request = request;
    }

    Payment(){ }
}
