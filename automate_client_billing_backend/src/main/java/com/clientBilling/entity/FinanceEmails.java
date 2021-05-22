package com.clientBilling.entity;

import jnr.ffi.annotations.In;
import lombok.Data;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.persistence.*;

@Data
@Entity
@Table
public class FinanceEmails {
    @Id
    @Column(name = "serial_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serialID;

    @Column(name = "email")
    private String email;

    @Column(name = "designation")
    private String designation;

    public FinanceEmails(Integer serialID, String email, String designation) {
        this.serialID = serialID;
        this.email = email;
        this.designation = designation;
    }

    FinanceEmails(){}
}
