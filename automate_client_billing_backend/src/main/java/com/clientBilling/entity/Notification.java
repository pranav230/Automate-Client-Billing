package com.clientBilling.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificationID")
    private Integer notificationID;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "defaultParam")
    private Integer defaultParam;

    public Notification(Integer notificationID, Timestamp timestamp, String type, String name, String status) {
        this.notificationID = notificationID;
        this.timestamp = timestamp;
        this.type = type;
        this.name = name;
        this.status = status;
        this.defaultParam = 0;
    }

    Notification(){}
}
