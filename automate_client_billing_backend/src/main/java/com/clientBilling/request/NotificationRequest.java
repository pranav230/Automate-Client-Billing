package com.clientBilling.request;

import lombok.Data;

@Data
public class NotificationRequest {
    private String type;
    private String name;
    private String status;

    public NotificationRequest(String type, String name, String status) {
        this.type = type;
        this.name = name;
        this.status = status;
    }
}
