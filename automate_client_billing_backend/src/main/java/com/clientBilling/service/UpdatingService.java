package com.clientBilling.service;

import com.clientBilling.entity.ClientPayment;
import com.clientBilling.entity.EmployeeProject;
import com.clientBilling.entity.Project;
import com.clientBilling.entity.Request;
import com.clientBilling.repository.ClientPaymentRepository;
import com.clientBilling.repository.EmployeeProjectRepository;
import com.clientBilling.repository.ProjectRepository;
import com.clientBilling.repository.RequestRepository;
import com.clientBilling.request.ClientMailRequest;
import com.clientBilling.request.NotificationRequest;
import com.clientBilling.response.ConfigResponse;
import com.clientBilling.response.EmployeeConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdatingService {
    private final ClientPaymentRepository clientPaymentRepository;
    private final RequestRepository requestRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeProjectRepository employeeProjectRepository;
    private final AddingDataService addingDataService;

    public Integer findIDForProject(Integer projectID, Integer month, Integer year){
        List<Request> requestList = requestRepository.findRequestByProjectID(projectID);
        requestList = requestList.stream().filter(request -> request.getMonthOfYear().getMonthValue() == month && request.getMonthOfYear().getYear() == year).collect(Collectors.toList());
        Integer id = requestList.size() == 0 ? null : requestList.get(0).getRequestId();

        return id;
    }

    public String updateClientReminder(Integer code, Integer month, Integer year){
        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(findIDForProject(code,month,year));
        clientPayment.setClientReminder(clientPayment.getClientReminder()+1);
        clientPayment = clientPaymentRepository.save(clientPayment);

        return "Now Client Reminder are " + clientPayment.getClientReminder();
    }

    public String updateTeamLeadReminder(Integer code,Integer month, Integer year){
        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(findIDForProject(code,month,year));
        clientPayment.setTeamLeadReminder(clientPayment.getTeamLeadReminder()+1);
        clientPayment = clientPaymentRepository.save(clientPayment);
        return "Now Team Lead Reminder are " + clientPayment.getTeamLeadReminder();
    }

    public String updateLeadStatus(Integer code, Integer month, Integer year,String status){
        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(findIDForProject(code,month,year));
        clientPayment.setTeamLeadStatus(status);

        if(status == "Approved") {
            addingDataService.addNotification(new NotificationRequest("teamLead",projectRepository.findByProjectId(code).getEmployee().getEmployeeName(),"Approved"));
        }

        clientPayment = clientPaymentRepository.save(clientPayment);
        return "Status has been updated to " + status + " successfully";
    }


    public String updateClientFinanceComment(ClientMailRequest clientMailRequest,Integer month,Integer year){
        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(findIDForProject(clientMailRequest.getProjectId(),month,year));
        clientPayment.setClientFinanceComment(clientMailRequest.getToClientComment());
        clientPayment = clientPaymentRepository.save(clientPayment);

        return "Comment: " + clientMailRequest.getToClientComment() + " has been updated in records";
    }


    public String updateConfigDetails(ConfigResponse configResponse, Integer month, Integer year){
        Project project = configResponse.getProject();
        projectRepository.save(project);

        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(findIDForProject(configResponse.getProject().getProjectID(),month,year));

        clientPayment.setClientTotalPayment(configResponse.getTotalPayment());
        clientPayment.setClientFinanceComment(configResponse.getClientFinanceComment());
        clientPayment.setFinanceClientComment(configResponse.getFinanceClientComment());
        clientPayment.setTeamManagerComment(configResponse.getTeamManagerComment());
        clientPayment.setFinanceManagerComment(configResponse.getFinanceComment());
        clientPaymentRepository.save(clientPayment);

        List<EmployeeConfig> employeeConfigList = configResponse.getEmployeeConfigList();
        for(EmployeeConfig employeeConfig: employeeConfigList){
            EmployeeProject employeeProject = employeeProjectRepository.findBySerialId(employeeConfig.getSerialID());
            employeeProject.setCurrentMonthLeaves(employeeConfig.getCurrentMonthLeaves());
            employeeProject.setEndDate(employeeConfig.getEndDate());
            employeeProject.setLeaves(employeeConfig.getLeaves());
            employeeProject.setStartDate(employeeConfig.getStartDate());
            employeeProject.setTotalLeaves(employeeConfig.getLeavesTotal());
            employeeProject.setRatePerHour(employeeConfig.getRatePerHour());
            employeeProject.setHoursBilled(employeeConfig.getHoursBilled());
            employeeProject.setIndividualPay(employeeConfig.getIndividualPay());
            employeeProjectRepository.save(employeeProject);
        }

        return "Details have been saved successfully in the database";
    }

    public String updateFinanceClientComment(ClientMailRequest clientMailRequest, Integer month, Integer year){
        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(findIDForProject(clientMailRequest.getProjectId(),month,year));
        clientPayment.setFinanceClientComment(clientMailRequest.getToClientComment());
        clientPayment = clientPaymentRepository.save(clientPayment);

        return "Comment: " + clientMailRequest.getToClientComment() + " has been updated in records";
    };

    public String updateClientStatus(Integer projectId, Integer month, Integer year,String status){
        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(findIDForProject(projectId,month,year));
        clientPayment.setClientStatus(status);

        if(status == "Approved") {
            addingDataService.addNotification(new NotificationRequest("client",projectRepository.findByProjectId(projectId).getClientName(),"Approved"));
        } else if(status == "Rejected"){
            addingDataService.addNotification(new NotificationRequest("client",projectRepository.findByProjectId(projectId).getClientName(),"Rejected"));
        }
        clientPayment = clientPaymentRepository.save(clientPayment);

        return "Status has been updated to " + status + " successfully";
    };
}
