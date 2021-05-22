package com.clientBilling;

import com.clientBilling.entity.*;
import com.clientBilling.repository.*;
import com.clientBilling.request.ClientMailRequest;
import com.clientBilling.service.AddingDataService;
import com.clientBilling.service.UpdatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class UpdateServiceTest {
    private UpdatingService updatingService;
    private ClientPayment clientPayment;
    private Project project;
    private Employee employee;
    private AddingDataService addingDataService;
    private List<Request> requestList;
    private Notification notification;
    private ClientMailRequest clientMailRequest;

    @Mock
    private ClientPaymentRepository clientPaymentRepository;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeProjectRepository employeeProjectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BandRepository bandRepository;

    @Mock
    private FinanceEmailRepository financeEmailRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private TemporaryRepository temporaryRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setup(){
        requestList = new ArrayList<>();
        addingDataService = new AddingDataService(employeeRepository,
                projectRepository,
                bandRepository,
                employeeProjectRepository,
                requestRepository,
                clientPaymentRepository,
                financeEmailRepository,
                notificationRepository,
                temporaryRepository,
                paymentRepository);
        updatingService = new UpdatingService(clientPaymentRepository,requestRepository,projectRepository,employeeProjectRepository,addingDataService);
        employee = new Employee("IN2012124","Pranav","SDE-II","Delivery","pranav.bansal@hashedin.com","Band8");
        project = new Project(9,"The Client Bill","","Tata","tata@gmail.com",employee,23000,
                "23E",
                LocalDate.of(2021,04,18),
                7.9);
        requestList.add(new Request(8,project,LocalDate.now()));
        clientPayment = new ClientPayment(1,
                new Request(8,project,LocalDate.of(2001,1,20)),
                "Approved",7000,"Approved","Approved","Approved","Approved","Approved",4 ,5,new Timestamp(System.currentTimeMillis()));
        notification = new Notification(1,new Timestamp(System.currentTimeMillis()),"client","astik","Approved");
        clientMailRequest = new ClientMailRequest(8,"Approved");
    }

    @Test
    void test_ifClientReminderIsUpdated(){
        Mockito.when(clientPaymentRepository.findClientByRequestID(any())).thenReturn(clientPayment);
        Mockito.when(clientPaymentRepository.save(any(ClientPayment.class))).thenReturn(clientPayment);

        String result = updatingService.updateClientReminder(123,3,2021);

        assertThat(result,equalTo("Now Client Reminder are 5"));
    }

    @Test
    void test_ifTeamLeadReminderIsUpdated(){
        Mockito.when(clientPaymentRepository.findClientByRequestID(any())).thenReturn(clientPayment);
        Mockito.when(clientPaymentRepository.save(any(ClientPayment.class))).thenReturn(clientPayment);

        String result = updatingService.updateTeamLeadReminder(123,3,2011);

        assertThat(result,equalTo("Now Team Lead Reminder are 6"));
    }

    @Test
    void test_ifCorrectRequestIdReturned(){
        Mockito.when(requestRepository.findRequestByProjectID(9)).thenReturn(requestList);

        Integer result = updatingService.findIDForProject(9,5,2021);

        assertThat(result,equalTo(8));
    }

    @Test
    void test_ifLeadStatusIsUpdated(){
        Mockito.when(clientPaymentRepository.findClientByRequestID(any())).thenReturn(clientPayment);
        Mockito.when(projectRepository.findByProjectId(any())).thenReturn(project);
        Mockito.when(clientPaymentRepository.save(any(ClientPayment.class))).thenReturn(clientPayment);
        Mockito.when(notificationRepository.save(any())).thenReturn(notification);

        String result = updatingService.updateLeadStatus(9,5,2021,"Approved");

        assertThat(result,equalTo("Status has been updated to Approved successfully"));
    }

    @Test
    void test_ifClientFinanceCommentUpdated(){
        Mockito.when(clientPaymentRepository.findClientByRequestID(any())).thenReturn(clientPayment);
        Mockito.when(clientPaymentRepository.save(any(ClientPayment.class))).thenReturn(clientPayment);

        String result = updatingService.updateClientFinanceComment(clientMailRequest,5,2021);

        assertThat(result,equalTo("Comment: Approved has been updated in records"));
    }

    @Test
    void test_ifClientStatusIsUpdated(){
        Mockito.when(clientPaymentRepository.findClientByRequestID(any())).thenReturn(clientPayment);
        Mockito.when(projectRepository.findByProjectId(any())).thenReturn(project);
        Mockito.when(clientPaymentRepository.save(any(ClientPayment.class))).thenReturn(clientPayment);
        Mockito.when(notificationRepository.save(any())).thenReturn(notification);

        String result = updatingService.updateClientStatus(9,5,2021,"Approved");

        assertThat(result,equalTo("Status has been updated to Approved successfully"));
    }

    @Test
    void test_ifFinanceClientCommentUpdated(){
        Mockito.when(clientPaymentRepository.findClientByRequestID(any())).thenReturn(clientPayment);
        Mockito.when(clientPaymentRepository.save(any(ClientPayment.class))).thenReturn(clientPayment);

        String result = updatingService.updateFinanceClientComment(clientMailRequest,5,2021);

        assertThat(result,equalTo("Comment: Approved has been updated in records"));
    }
}
