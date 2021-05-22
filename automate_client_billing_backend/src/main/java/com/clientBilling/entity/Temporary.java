package com.clientBilling.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Table
@Data
@Entity
public class Temporary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serialId")
    private Integer serialId;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public Temporary(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Temporary() {
    }
}
