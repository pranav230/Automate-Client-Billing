package com.clientBilling.controller;

import com.clientBilling.request.ClientMailRequest;
import com.clientBilling.service.EmailService;
import com.clientBilling.service.UpdatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.IOException;

@Controller
public class EmailSenderController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UpdatingService updatingService;

    public EmailSenderController(EmailService emailService,UpdatingService updatingService){
        this.emailService=emailService;
        this.updatingService = updatingService;
    }

    @RequestMapping("/sendClientPaymentMail/{month}/{year}")
    public @ResponseBody
    void sendClientPaymentMail(@RequestBody ClientMailRequest clientMailRequest, @PathVariable("month") Integer month, @PathVariable("year") Integer year) throws Exception {
        updatingService.updateFinanceClientComment(clientMailRequest,month,year);
        emailService.sendClientMail(clientMailRequest.getProjectId(),month,year,"client-payment-email-template","Client Payment");
        updatingService.updateClientStatus(clientMailRequest.getProjectId(),month,year,"Pending");
    }

    @RequestMapping("/sendClientReminder/{month}/{year}")
    public @ResponseBody
    void sendClientReminder(@RequestBody Integer projectID, @PathVariable("month") Integer month, @PathVariable("year") Integer year) throws MessagingException, IOException {
        emailService.sendClientMail(projectID,month,year,"client-reminder-email-template","Follow up message for project approval");
        updatingService.updateClientReminder(projectID,month,year);
    }

    @RequestMapping("/sendLeadReminder/{month}/{year}")
    public @ResponseBody
    void sendLeadReminder(@RequestBody Integer projectID, @PathVariable("month") Integer month, @PathVariable("year") Integer year) throws MessagingException, IOException {
        emailService.sendLeadMail(projectID,month,year,"lead-reminder-email-template","Follow up message for project approval");
        updatingService.updateTeamLeadReminder(projectID,month,year);
    }

    @RequestMapping("/sendLeadApprovalMail/{month}/{year}")
    public @ResponseBody
    void sendLeadApprovalMail(@RequestBody Integer projectID, @PathVariable("month") Integer month, @PathVariable("year") Integer year) throws Exception {
        emailService.sendLeadMail(projectID,month,year,"lead-approval-email-template","Project Approval");
        updatingService.updateLeadStatus(projectID,month,year,"Pending");
    }
}
