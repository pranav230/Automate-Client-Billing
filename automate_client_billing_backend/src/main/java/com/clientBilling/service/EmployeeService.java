package com.clientBilling.service;

import com.clientBilling.converters.EmployeeConverter;
import com.clientBilling.entity.*;
import com.clientBilling.repository.*;
import com.clientBilling.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final ProjectRepository projectRepository;
    private final BandRepository bandRepository;
    private final EmployeeProjectRepository employeeProjectRepository;
    private final RequestRepository requestRepository;
    private final ClientPaymentRepository clientPaymentRepository;
    private final NotificationRepository notificationRepository;
    private final TemporaryRepository temporaryRepository;
    private final UpdatingService updatingService;
    private final PaymentRepository paymentRepository;
    private final FinanceEmailRepository financeEmailRepository;

    public List<BilldetailsResponse> getBillDetails(Integer month, Integer year){
        List<BilldetailsResponse> billDetailResult = new ArrayList<>();
        BilldetailsResponse billdetails;
        EmployeeBilldetailsResponse employeeBillDetails;
        List<Project> projectList;
        projectList = (List<Project>) projectRepository.findAll();
        for(Project pro:projectList){
            List<EmployeeProject> employeeProjectsList= employeeProjectRepository.findByProjectID(pro.getProjectID());

            ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(updatingService.findIDForProject(pro.getProjectID(),month,year));
            int totalEmployee = employeeProjectsList.size();
            List<EmployeeBilldetailsResponse> employeeBilldetailsResponseList = new ArrayList<>();
            for(EmployeeProject emp:employeeProjectsList){
                if(emp.getMonthOfYear().getMonthValue() == month && emp.getMonthOfYear().getYear() == year) {
                    employeeBillDetails = EmployeeConverter.toEmployeeBillDetailsResponse(emp);
                    employeeBilldetailsResponseList.add(employeeBillDetails);
                }
            }
            if(employeeBilldetailsResponseList.size() != 0) {
                billdetails = EmployeeConverter.toBillDetailsResponse(pro, clientPayment.getClientTotalPayment(), totalEmployee, employeeBilldetailsResponseList,clientPayment.getTeamLeadStatus());
                billDetailResult.add(billdetails);
            }
        }
        return billDetailResult;
    }

    public List<TeamLeadResponse> leadResponse(Integer month, Integer year){
        List<TeamLeadResponse> teamLeadList = new ArrayList<>();

        List<Project> projectList = (List<Project>) projectRepository.findAll();

        for(Project project: projectList){

            List<Request> requestList = requestRepository.findRequestByProjectID(project.getProjectID());
            requestList = requestList.stream().filter(request -> request.getMonthOfYear().getMonthValue() == month && request.getMonthOfYear().getYear() == year).collect(Collectors.toList());
            Integer id = requestList.size() == 0 ? null : requestList.get(0).getRequestId();

            if(id != null){
                TeamLeadResponse response = new TeamLeadResponse(project.getEmployee().getEmployeeID(),
                        project.getEmployee().getEmployeeName(),
                        clientPaymentRepository.findTeamLeadStatusByRequestID(id),
                        project.getClientName(),
                        project.getProjectTitle(),
                        project.getProjectID(),
                        clientPaymentRepository.findTeamReminderByRequestID(id)
                );

                teamLeadList.add(response);
            }
        }

        return teamLeadList;
    }

    public List<ClientPaymentResponse> clientPaymentList(Integer month, Integer year){
        List<ClientPaymentResponse> clientPaymentResponseList = new ArrayList<>();

        List<ClientPayment> clientPaymentList = (List<ClientPayment>) clientPaymentRepository.findAll();
        for(ClientPayment clientPayment : clientPaymentList){
            if(clientPayment.getRequest().getMonthOfYear().getMonthValue() == month && clientPayment.getRequest().getMonthOfYear().getYear() == year) {
                Integer projectId = requestRepository.findProjectByRequestID(clientPayment.getRequest().getRequestId());
                ClientPaymentResponse response = new ClientPaymentResponse(clientPayment.getRequest().getRequestId(),
                        clientPayment.getClientTotalPayment(),
                        clientPayment.getClientStatus(),
                        projectRepository.findByProjectId(projectId).getClientName(),
                        projectRepository.findByProjectId(projectId).getProjectTitle(),
                        clientPayment.getRequest().getProject().getProjectID(),
                        clientPayment.getClientReminder(),
                        clientPayment.getTimestamp()
                );

                clientPaymentResponseList.add(response);
            }
        }

        return clientPaymentResponseList;
    }

    public ProjectDetails projectDetails(Integer code,Integer month,Integer year){
        EmployeeBilldetailsResponse employeeBillDetails;

        Project project= projectRepository.findByProjectId(code);
        List<Request> requestList = requestRepository.findRequestByProjectID(code);
        requestList = requestList.stream().filter(request -> request.getMonthOfYear().getMonthValue() == month && request.getMonthOfYear().getYear() == year).collect(Collectors.toList());
        Integer id = requestList.size() == 0 ? null : requestList.get(0).getRequestId();

        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(id);

        List<EmployeeProject> employeeProjectsList = employeeProjectRepository.findByProjectID(project.getProjectID());
        int totalEmployee = employeeProjectsList.size();

        List<EmployeeBilldetailsResponse> employeeBilldetailsResponseList = new ArrayList<>();

        for (EmployeeProject emp : employeeProjectsList) {
            employeeBillDetails = EmployeeConverter.toEmployeeBillDetailsResponse(emp);
            employeeBilldetailsResponseList.add(employeeBillDetails);
        }

        return new ProjectDetails(project.getClientName(),
                project.getProjectTitle(),
                project.getEmployee().getEmployeeID(),
                project.getEmployee().getEmployeeName(),
                clientPayment.getTeamLeadStatus(),
                clientPayment.getClientStatus(),
                clientPayment.getClientTotalPayment(),
                clientPayment.getTeamLeadReminder(),
                clientPayment.getClientReminder(),
                clientPayment.getTeamManagerComment(),
                clientPayment.getClientFinanceComment(),
                clientPayment.getFinanceClientComment(),
                totalEmployee,
                paymentRepository.findModeByRequestID(id),
                employeeBilldetailsResponseList
                );
    }

    public List<HomePageResponse> getHomePageDetails(Integer month, Integer year){
        List<HomePageResponse> homePageResult = new ArrayList<>();

        List<Project> projectlist= (List<Project>) projectRepository.findAll();
        for(Project pro:projectlist) {
            List<EmployeeProject> employeeProjectsList = employeeProjectRepository.findByProjectID(pro.getProjectID());
            int totalEmployee = employeeProjectsList.size();
            List<Request> requestList = requestRepository.findRequestByProjectID(pro.getProjectID());

            Integer requestID = null;
            for(Request request: requestList) {
                if (request.getMonthOfYear().getMonthValue() == month && request.getMonthOfYear().getYear() == year) {
                    requestID = request.getRequestId();
                }
            }

            List<String> paymentStatus = new ArrayList<>();

            if (requestID != null) {
                paymentStatus.add(clientPaymentRepository.findTeamLeadStatusByRequestID(requestID));
                paymentStatus.add(clientPaymentRepository.findClientStatusByRequestID(requestID));

                if(paymentStatus.get(0) == null){
                    paymentStatus.set(0,"Request Not Generated");
                }
                if(paymentStatus.get(1) == null){
                    paymentStatus.set(1,"Request Not Generated");
                }
            } else {
                paymentStatus.add("Request Not Generated");
                paymentStatus.add("Request Not Generated");
            }

            HomePageResponse homePageDetails = EmployeeConverter.toHomePageResponse(pro,paymentStatus,totalEmployee);

            homePageResult.add(homePageDetails);
        }

        return homePageResult;
    }

    public NotificationResponse getAllNotifications(){
        List<Notification> notificationList = (List<Notification>) notificationRepository.findAll();
        Collections.reverse(notificationList);
        Timestamp timestamp = ((List<Temporary>)temporaryRepository.findAll()).get(0).getTimestamp();
        List<Notification> notificationList1 = notificationList.stream().filter(notification -> {
            if(notification.getTimestamp().compareTo(timestamp) >= 0) {
                return true;
            }
            return false;
        }
        ).collect(Collectors.toList());

        NotificationResponse notificationResponse = new NotificationResponse(notificationList,notificationList1.size());

        System.out.println(notificationList);
        return notificationResponse;
    }

    public List<Notification> getNotifications(){
        List<Notification> notificationList = (List<Notification>) notificationRepository.findAll();
        Timestamp timestamp = ((List<Temporary>)temporaryRepository.findAll()).get(0).getTimestamp();
        Collections.reverse(notificationList);
        notificationList = notificationList.stream().filter(notification -> {
                    if(notification.getTimestamp().compareTo(timestamp) >= 0) {
                        return true;
                    }
                    return false;
                }
        ).collect(Collectors.toList());

        return notificationList;
    }

    public ConfigResponse configDetails(Integer code, Integer month, Integer year){
        Project project= projectRepository.findByProjectId(code);
        List<Request> requestList = requestRepository.findRequestByProjectID(code);
        requestList = requestList.stream().filter(request -> request.getMonthOfYear().getMonthValue() == month && request.getMonthOfYear().getYear() == year).collect(Collectors.toList());
        Integer id = requestList.size() == 0 ? null : requestList.get(0).getRequestId();

        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(id);

        List<EmployeeProject> employeeProjectsList = employeeProjectRepository.findByProjectID(project.getProjectID());
        List<EmployeeConfig> employeeConfigList = new ArrayList<>();
        for(EmployeeProject employee: employeeProjectsList){
            if(employee.getMonthOfYear().getMonthValue() == month && employee.getMonthOfYear().getYear() == year) {
                EmployeeConfig employeeConfig = new EmployeeConfig(employee.getStartDate(),
                        employee.getEndDate(),
                        employee.getEmployee().getEmployeeID(),
                        employee.getEmployee().getEmployeeName(),
                        employee.getEmployee().getDesignation(),
                        employee.getLeaves(),
                        employee.getCurrentMonthLeaves(),
                        employee.getTotalLeaves(),
                        employee.getRatePerHour(),
                        employee.getHoursBilled(),
                        employee.getSerialID(),
                        employee.getIndividualPay()
                );
                employeeConfigList.add(employeeConfig);
            }
        }

        return new ConfigResponse(project,
                project.getEmployee().getEmail(),
                clientPayment.getClientTotalPayment(),
                clientPayment.getTeamManagerComment(),
                clientPayment.getFinanceManagerComment(),
                clientPayment.getFinanceClientComment(),
                clientPayment.getClientFinanceComment(),
                clientPayment.getTeamLeadStatus(),
                employeeConfigList);
    }

    public PaymentResponse getTotalPayment(Integer code, Integer month, Integer year){
        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(updatingService.findIDForProject(code,month,year));
        return new PaymentResponse(clientPayment.getClientTotalPayment(),clientPayment.getClientStatus());
    }

    public TransactionResponse getTransactionSummary(Integer code, Integer month, Integer year){
        Integer id = updatingService.findIDForProject(code,month,year);

        ClientPayment clientPayment = clientPaymentRepository.findClientByRequestID(id);
        Payment payment = paymentRepository.findPaymentByRequestID(id);

        return EmployeeConverter.toTransactionResponse(clientPayment,payment);
    }

    public String getRejectionMessage(Integer code, Integer month, Integer year){
        return clientPaymentRepository.findClientByRequestID(updatingService.findIDForProject(code,month,year)).getClientFinanceComment();
    }

    public FinanceEmails verifyUser(String email) {
        FinanceEmails obj = financeEmailRepository.findEmailById(email);
        return obj;
    }
}
