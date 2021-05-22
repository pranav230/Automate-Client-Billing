package com.clientBilling.response;

import lombok.Data;

import java.util.List;

@Data
public class HomePageResponse {
    private String projectName;
    private String clientName;
    private Integer totalEmployees;
    private List<String> paymentStatus;

    public HomePageResponse(String projectName, String clientName, Integer totalEmployees, List<String> paymentStatus) {
        this.projectName = projectName;
        this.clientName = clientName;
        this.totalEmployees = totalEmployees;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "HomePageResponse{" +
                "projectName='" + projectName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", totalEmployees=" + totalEmployees +
                ", paymentStatus=" + paymentStatus.toString() +
                '}';
    }
}
