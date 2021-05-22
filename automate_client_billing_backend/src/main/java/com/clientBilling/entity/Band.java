package com.clientBilling.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@Data
public class Band {
    @Id
    @Column(name = "band_level")
    private String bandLevel;

    @Column(name = "rate_per_hour")
    private Integer ratePerHour;

    Band(){}

    public Band(String bandLevel, Integer ratePerHour) {
        this.bandLevel = bandLevel;
        this.ratePerHour = ratePerHour;
    }
}