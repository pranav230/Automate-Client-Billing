package com.clientBilling.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="EmployeeDetails")
public class Employee {
    @Id
    @Column(name = "EmployeeID")
    private String employeeID;

    @Column(name = "Employee_Name")
    private String employeeName;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    private String department;

    @Column(name = "email")
    private String email;

    @Column(name = "Band")
    private String band;

    public Employee(){}

    public Employee(String employeeID, String employeeName, String designation, String department, String email, String band) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.designation = designation;
        this.department = department;
        this.email = email;
        this.band = band;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID='" + employeeID + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", designation='" + designation + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", band='" + band + '\'' +
                '}';
    }
}
