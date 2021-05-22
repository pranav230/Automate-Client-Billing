package com.clientBilling.response;

import lombok.Data;

@Data
public class TeamLeadResponse {
    private String teamLeadID;
    private String teamLeadname;
    private String feedback;
    private String clientName;
    private String projectName;
    private Integer projectID;
    private Integer teamLeadReminder;

    public TeamLeadResponse(String teamLeadID, String teamLeadname, String feedback, String clientName, String projectName, Integer projectID, Integer teamLeadReminder) {
        this.teamLeadID = teamLeadID;
        this.teamLeadname = teamLeadname;
        this.feedback = feedback;
        this.clientName = clientName;
        this.projectName = projectName;
        this.projectID = projectID;
        this.teamLeadReminder = teamLeadReminder;
    }
}
