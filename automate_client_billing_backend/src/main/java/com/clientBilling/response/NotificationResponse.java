package com.clientBilling.response;

import com.clientBilling.entity.Notification;
import lombok.Data;

import java.util.List;

@Data
public class NotificationResponse {
    private List<Notification> notificationList;
    private Integer newNotifications;

    public NotificationResponse(List<Notification> notificationList, Integer newNotifications) {
        this.notificationList = notificationList;
        this.newNotifications = newNotifications;
    }

    NotificationResponse(){

    }
}
