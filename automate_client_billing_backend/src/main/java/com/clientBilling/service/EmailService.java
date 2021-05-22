package com.clientBilling.service;

import com.clientBilling.entity.ClientPayment;
import com.clientBilling.entity.Project;
import com.clientBilling.entity.Request;
import com.clientBilling.repository.ClientPaymentRepository;
import com.clientBilling.repository.ProjectRepository;
import com.clientBilling.repository.RequestRepository;
import com.clientBilling.response.ConfigResponse;
import com.clientBilling.response.EmployeeConfig;
import com.clientBilling.response.ProjectDetails;
import com.sun.jdi.VMOutOfMemoryException;
import jnr.ffi.annotations.In;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmployeeService employeeService;
    private final UpdatingService updatingService;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender sender;

    public void sendClientMail(Integer projectId,Integer month, Integer year, String template,String subject) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        ConfigResponse details = employeeService.configDetails(projectId,month,year);

        String link = "https://automate-billing-frontend-dot-hu18-groupa-angular.et.r.appspot.com/finalPayment/"+projectId+"/"+month+"/"+year;

        Map<String, Object> model = new HashMap<>();
        String [][] dateList = new String[details.getEmployeeConfigList().size()][2];
        for(int i=0;i<details.getEmployeeConfigList().size();i++){
            dateList[i][0] = details.getEmployeeConfigList().get(i).getStartDate().getMonth().toString() + " " + details.getEmployeeConfigList().get(i).getStartDate().getDayOfMonth();
            dateList[i][1] = details.getEmployeeConfigList().get(i).getEndDate().getMonth().toString() + " " + details.getEmployeeConfigList().get(i).getEndDate().getDayOfMonth();
        }
        model.put("id",updatingService.findIDForProject(projectId,month,year));
        model.put("dateList",dateList);
        model.put("projectName",details.getProject().getProjectTitle());
        model.put("employeeList",details.getEmployeeConfigList());
        model.put("month",details.getEmployeeConfigList().get(0).getEndDate().getMonth());
        model.put("comment",details.getFinanceClientComment());
        model.put("total",details.getTotalPayment());
        model.put("link",link);

        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process(template, context);

        try {
            helper.setTo("astik.dubey@hashedin.com");
            helper.setText(html,true);
            helper.setSubject(subject);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

        sender.send(message);
    }

    public void sendLeadMail(Integer projectId,Integer month, Integer year, String template, String subject) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        ConfigResponse details = employeeService.configDetails(projectId,month,year);

        String link = "https://automate-billing-frontend-dot-hu18-groupa-angular.et.r.appspot.com/manager/"+projectId+"/"+month+"/"+year;

        Map<String, Object> model = new HashMap<>();
        String [][] dateList = new String[details.getEmployeeConfigList().size()][2];
        for(int i=0;i<details.getEmployeeConfigList().size();i++){
            dateList[i][0] = details.getEmployeeConfigList().get(i).getStartDate().getMonth().toString() + " " + details.getEmployeeConfigList().get(i).getStartDate().getDayOfMonth();
            dateList[i][1] = details.getEmployeeConfigList().get(i).getEndDate().getMonth().toString() + " " + details.getEmployeeConfigList().get(i).getEndDate().getDayOfMonth();
        }
        model.put("dateList",dateList);
        model.put("clientName",details.getProject().getClientName());
        model.put("projectName",details.getProject().getProjectTitle());
        model.put("teamLeadMailId",details.getProject().getEmployee().getEmail());
        model.put("poNumber",details.getProject().getPoNumber());
        model.put("poBalance",details.getProject().getPoBalance());
        model.put("poExpiry",details.getProject().getPoExpiry());
        model.put("employeeList",details.getEmployeeConfigList());
        model.put("comment",details.getFinanceComment());
        model.put("total",details.getTotalPayment());
        model.put("link",link);

        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process(template, context);

        try {
            helper.setTo("astik.dubey@hashedin.com");
            helper.setText(html,true);
            helper.setSubject(subject);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

        sender.send(message);
    }
}

