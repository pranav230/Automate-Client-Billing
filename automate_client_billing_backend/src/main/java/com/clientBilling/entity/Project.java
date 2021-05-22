package com.clientBilling.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ProjectDetails")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID")
    private Integer projectID;

    @Column(name = "Project_Title")
    private String projectTitle;

    @Column(name = "Description")
    private String projectDesc;

    @Column(name = "ClientName")
    private String clientName;

    @Column(name = "client_email")
    private String clientEmail;

    @OneToOne
    @JoinColumn(name = "teamLeadID")
    private Employee employee;

    @Column(name = "po_balance")
    private Integer poBalance;

    @Column(name = "po_number")
    private String poNumber;

    @Column(name = "po_expiry")
    private LocalDate poExpiry;

    @Column(name = "gst")
    private Double gst;

   public Project(){}

    public Project(Integer projectID, String projectTitle, String projectDesc, String clientName, String clientEmail, Employee employee, Integer poBalance, String poNumber, LocalDate poExpiry, double gst) {
        this.projectID = projectID;
        this.projectTitle = projectTitle;
        this.projectDesc = projectDesc;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.employee = employee;
        this.poBalance = poBalance;
        this.poNumber = poNumber;
        this.poExpiry = poExpiry;
        this.gst = gst;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectID=" + projectID +
                ", projectTitle='" + projectTitle + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", employee=" + employee +
                ", poBalance=" + poBalance +
                ", poNumber='" + poNumber + '\'' +
                ", poExpiry=" + poExpiry +
                ", gst=" + gst +
                '}';
    }
}
