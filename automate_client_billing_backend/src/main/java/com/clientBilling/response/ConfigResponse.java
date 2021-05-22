package com.clientBilling.response;

import com.clientBilling.entity.Project;
import lombok.Data;

import java.util.List;

@Data
public class ConfigResponse {
    private Project project;
    private String managerEmail;
    private Integer totalPayment;
    private String teamManagerComment;
    private String financeComment;
    private String financeClientComment;
    private String clientFinanceComment;
    private String teamleadStatus;
    private List<EmployeeConfig> employeeConfigList;

    public ConfigResponse(Project project, String managerEmail, Integer totalPayment, String teamManagerComment, String financeComment, String financeClientComment, String clientFinanceComment, String teamleadStatus, List<EmployeeConfig> employeeConfigList) {
        this.project = project;
        this.managerEmail = managerEmail;
        this.totalPayment = totalPayment;
        this.teamManagerComment = teamManagerComment;
        this.financeComment = financeComment;
        this.financeClientComment = financeClientComment;
        this.clientFinanceComment = clientFinanceComment;
        this.teamleadStatus = teamleadStatus;
        this.employeeConfigList = employeeConfigList;
    }
}
