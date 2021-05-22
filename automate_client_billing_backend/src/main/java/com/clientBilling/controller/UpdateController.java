package com.clientBilling.controller;

import com.clientBilling.request.ClientMailRequest;
import com.clientBilling.response.ConfigResponse;
import com.clientBilling.service.UpdatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {
    @Autowired
    private UpdatingService updatingService;

    public void UpdateController(UpdatingService updatingService){
        this.updatingService = updatingService;
    }

    @PatchMapping("/config/{month}/{year}")
    public String updateConfigDetails(@RequestBody ConfigResponse configResponse,@PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        return updatingService.updateConfigDetails(configResponse,month,year);
    }

    @PatchMapping("/updateManager/{month}/{year}")
    public String updateLeadStatus(@RequestBody Integer projectID,@PathVariable("month") Integer month, @PathVariable("year") Integer year){
        return updatingService.updateLeadStatus(projectID,month,year,"Approved");
    }

    @PatchMapping("/updateRejectClientPayment/{month}/{year}")
    public String updateRejectClientPayment(@RequestBody ClientMailRequest clientMailRequest, @PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        updatingService.updateClientStatus(clientMailRequest.getProjectId(),month,year,"Rejected");
        return updatingService.updateClientFinanceComment(clientMailRequest,month,year);
    }
}
