package com.clientBilling.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table
@Entity
public class EmployeeProject {
    @Id
    @Column(name = "SerialID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serialID;

    @OneToOne
    @JoinColumn(name = "EmployeeID")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "ProjectID")
    private Project project;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "leaves")
    private Integer leaves;

    @Column(name = "current_month_leaves")
    private Integer currentMonthLeaves;

    @Column(name = "total_leaves")
    private Integer totalLeaves;

    @Column(name = "month_year")
    private LocalDate monthOfYear;

    @Column(name = "percent")
    private Double percent;

    @Column(name = "rate_per_hour")
    private Integer ratePerHour;

    @Column(name = "hours_billed")
    private Integer hoursBilled;

    @Column(name = "individualPay")
    private Integer individualPay;

    public EmployeeProject(){}

    public EmployeeProject(Integer serialID, Employee employee, Project project, LocalDate startDate, LocalDate endDate, Integer leaves, Integer currentMonthLeaves, Integer totalLeaves, LocalDate monthOfYear, Double percent, Integer ratePerHour, Integer hoursBilled, Integer individualPay) {
        this.serialID = serialID;
        this.employee = employee;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaves = leaves;
        this.currentMonthLeaves = currentMonthLeaves;
        this.totalLeaves = totalLeaves;
        this.monthOfYear = monthOfYear;
        this.percent = percent;
        this.ratePerHour = ratePerHour;
        this.hoursBilled = hoursBilled;
        this.individualPay = individualPay;
    }

    @Override
    public String toString() {
        return "EmployeeProject{" +
                "serialID=" + serialID +
                ", employee=" + employee +
                ", project=" + project +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", leaves=" + leaves +
                ", currentMonthLeaves=" + currentMonthLeaves +
                ", totalLeaves=" + totalLeaves +
                ", monthOfYear=" + monthOfYear +
                ", percent=" + percent +
                ", ratePerHour=" + ratePerHour +
                ", hoursBilled=" + hoursBilled +
                ", individualPay=" + individualPay +
                '}';
    }
}
