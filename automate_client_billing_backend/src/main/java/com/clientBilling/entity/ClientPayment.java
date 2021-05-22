package com.clientBilling.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Table
@Entity
public class ClientPayment{
    @Id
    @Column(name = "PaymentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @OneToOne
    @JoinColumn(name = "RequestId")
    private Request request;

    @Column(name = "team_lead_status")
    private String teamLeadStatus;

    @Column(name = "client_total_payment")
    private Integer clientTotalPayment;

    @Column(name = "team_manager_comment")
    private String teamManagerComment;

    @Column(name = "client_finance_comment")
    private String clientFinanceComment;

    @Column(name = "finance_manager_comment")
    private String financeManagerComment;

    @Column(name = "finance_client_comment")
    private String financeClientComment;

    @Column(name = "client_status")
    private String clientStatus;

    @Column(name = "client_reminder")
    private Integer clientReminder;

    @Column(name = "team_lead_reminder")
    private Integer teamLeadReminder;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public ClientPayment(Integer paymentId, Request request, String teamLeadStatus, Integer clientTotalPayment, String teamManagerComment, String clientFinanceComment, String financeManagerComment, String financeClientComment, String clientStatus, Integer clientReminder, Integer teamLeadReminder, Timestamp timestamp) {
        this.paymentId = paymentId;
        this.request = request;
        this.teamLeadStatus = teamLeadStatus;
        this.clientTotalPayment = clientTotalPayment;
        this.teamManagerComment = teamManagerComment;
        this.clientFinanceComment = clientFinanceComment;
        this.financeManagerComment = financeManagerComment;
        this.financeClientComment = financeClientComment;
        this.clientStatus = clientStatus;
        this.clientReminder = clientReminder;
        this.teamLeadReminder = teamLeadReminder;
        this.timestamp = timestamp;
    }

    public ClientPayment(){}
}
