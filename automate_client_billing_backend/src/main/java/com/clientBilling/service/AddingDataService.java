package com.clientBilling.service;

import com.clientBilling.converters.EmployeeConverter;
import com.clientBilling.entity.*;
import com.clientBilling.repository.*;
import com.clientBilling.request.EmployeeRequest;
import com.clientBilling.request.NotificationRequest;
import com.clientBilling.request.PaymentModeRequest;
import com.clientBilling.request.PaymentRequest;
import jnr.ffi.annotations.In;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddingDataService {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final BandRepository bandRepository;
    private final EmployeeProjectRepository employeeProjectRepository;
    private final RequestRepository requestRepository;
    private final ClientPaymentRepository clientPaymentRepository;
    private final FinanceEmailRepository financeEmailRepository;
    private final NotificationRepository notificationRepository;
    private final TemporaryRepository temporaryRepository;
    private final PaymentRepository paymentRepository;

    public String addData(EmployeeRequest employeeDetails){
        Employee employee = EmployeeConverter.toEmployeeEntity(employeeDetails);

        if(employeeRepository.findCountOfEmployeeID(employee.getEmployeeID()) == 0){
            employee = employeeRepository.save(employee);
        } else {
            employee = employeeRepository.findByEmployeeID(employee.getEmployeeID());
        }

        Employee teamLead = employeeRepository.findByEmployeeID(employeeDetails.getTeamLeadID());
        System.out.println(teamLead);
        Project project = EmployeeConverter.toProjectEntity(employeeDetails,teamLead);


        if(projectRepository.findCountByTitle(project.getProjectTitle()) == 0){
            project = projectRepository.save(project);
        } else{
            project = projectRepository.findByTitle(project.getProjectTitle());
        }

        Integer ratePerHour = bandRepository.findRateByBandLevel(employee.getBand());
        EmployeeProject employeeProject = EmployeeConverter.toEmployeeProjectEntity(employeeDetails,employee,project,ratePerHour);
        employeeProject = employeeProjectRepository.save(employeeProject);

        System.out.println(employeeProject);
        return "Employee "+ employee.getEmployeeID() +" data has been added successfully";
    }

    public String addBand(Band band){
        band = bandRepository.save(band);
        return band.getBandLevel() +" Level Saved";
    }

    public String createPayment(PaymentRequest paymentRequest){
        Project project = projectRepository.findByProjectId(paymentRequest.getProjectID());

        Request request = new Request(null, project, LocalDate.now());
        request = requestRepository.save(request);

        ClientPayment clientPayment = new ClientPayment(null,
                request,
                paymentRequest.getTeamLeadStatus(),
                paymentRequest.getClientTotalPayment(),
                paymentRequest.getTeamManagerComment(),
                paymentRequest.getClientFinanceComment(),
                paymentRequest.getFinanceManagerComment(),
                paymentRequest.getFinanceClientComment(),
                paymentRequest.getClientStatus(),
                paymentRequest.getClientReminder(),
                paymentRequest.getTeamLeadReminder(),
                new Timestamp(System.currentTimeMillis()));
        clientPayment = clientPaymentRepository.save(clientPayment);

        return "Client Payment for project "+ paymentRequest.getProjectID() +" has been added successfully";
    }

    public String addFinanceEmail(FinanceEmails financeEmails){
        FinanceEmails temp = financeEmailRepository.findEmailById(financeEmails.getEmail());
        if(temp != null){
            temp.setDesignation(financeEmails.getDesignation());
            financeEmailRepository.save(temp);
        } else{
            financeEmails = financeEmailRepository.save(financeEmails);
        }
        return "Email "+ financeEmails.getEmail() +" has been added to the db";
    }

    public String addNotification(NotificationRequest notificationRequest){
        Notification notification = EmployeeConverter.toNotification(notificationRequest);
        notification = notificationRepository.save(notification);
        return "Notification for "+ notification.getName() +" has been saved successfully.";
    }

    public String addTimeStamp(){
        List<Temporary> temporary = (List<Temporary>) temporaryRepository.findAll();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestamp = new Timestamp(timestamp.getTime() + (1000*60*60*5 + 1000*60*30));

        if(temporary.size() == 0) {
            Temporary temp = new Temporary(timestamp);
            temporaryRepository.save(temp);
        }
        else {
            temporary.get(0).setTimestamp(timestamp);
            temporaryRepository.save(temporary.get(0));
        }

        return "Timestamp"+ temporary.get(0).getTimestamp().toLocalDateTime().getMonth() +" has been saved";
    }

    public String addPayment(Integer projectId, Integer month, Integer year, PaymentModeRequest paymentMode){
        Project project= projectRepository.findByProjectId(projectId);
        List<Request> requestList = requestRepository.findRequestByProjectID(projectId);
        requestList = requestList.stream().filter(request -> request.getMonthOfYear().getMonthValue() == month && request.getMonthOfYear().getYear() == year).collect(Collectors.toList());
        Request request = requestList.size() == 0 ? null : requestList.get(0);

        Payment payment=EmployeeConverter.toPaymentEntity(paymentMode,request);

        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(request.getRequestId());

        clientPayment.setClientFinanceComment(paymentMode.getClientFinanceComment());
        clientPaymentRepository.save(clientPayment);
        paymentRepository.save(payment);

        return "Payment info of Request "+ clientPayment.getRequest().getRequestId() +" has been saved successfully";
    }
}
