package com.clientBilling.response;

import lombok.Data;

@Data
public class PaymentResponse {
    private Integer totalPayment;
    private String clientStatus;

    public PaymentResponse(Integer totalPayment, String clientStatus) {
        this.totalPayment = totalPayment;
        this.clientStatus = clientStatus;
    }
}
