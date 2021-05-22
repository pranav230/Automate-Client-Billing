package com.clientBilling.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RequestId")
    private Integer requestId;

    @ManyToOne
    @JoinColumn(name = "ProjectID")
    private Project project;

    @Column(name = "month_year")
    private LocalDate monthOfYear;

    public Request() {
    }

    public Request(Integer requestId, Project project, LocalDate monthOfYear) {
        this.requestId = requestId;
        this.project = project;
        this.monthOfYear = monthOfYear;
    }
}
