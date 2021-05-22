package com.clientBilling;

import com.clientBilling.converters.EmployeeConverter;
import com.clientBilling.entity.*;
import com.clientBilling.repository.*;
import com.clientBilling.request.EmployeeRequest;
import com.clientBilling.request.NotificationRequest;
import com.clientBilling.request.PaymentModeRequest;
import com.clientBilling.request.PaymentRequest;
import com.clientBilling.service.AddingDataService;
import com.clientBilling.service.UpdatingService;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class AddingServiceTest {

    private AddingDataService addingDataService;

    private EmployeeRequest employeeRequest;
    private NotificationRequest notificationRequest;
    private Employee employee;
    private Band band;
    private FinanceEmails financeEmails;
    private Project project;
    private PaymentRequest paymentRequest;

    private Notification notification;
    private List<Temporary> temporary;
    private PaymentModeRequest paymentModeRequest;
    private List<Request> requestList;
    private ClientPayment clientPayment;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BandRepository bandRepository;

    @Mock
    private EmployeeProjectRepository employeeProjectRepository;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private ClientPaymentRepository clientPaymentRepository;

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
        band = new Band( "Band7",48000);
        temporary = new ArrayList<>();
        requestList = new ArrayList<>();
        financeEmails = new FinanceEmails(null,"pranav.bansal@hashedin.com","head");
        paymentRequest = new PaymentRequest( 12,
                 1200,
                 "Pending","Approved",
                 "Approved",
                "Approved",
                 "Approved",
                "Approved",
                 "Approved",
                 3,
                 2);
        employeeRequest = new EmployeeRequest(
                 "IN2012002",
                "IN2012124",
                "Pranav",
                "SDE-II",
                "Delivery",
                "pranav@hashedin.com",
                "Band8",
                "Safe Headers",
                "No description",
                "Studs",
                LocalDate.of(2021,04,18),
                2,
                LocalDate.of(2021,01,18),
                LocalDate.of(2021,05,18),
                50.0,
                23000,
                "23E",
                LocalDate.of(2021,04,18),
                7.9,
                2,
                3,"studs@gmail.com");
        employee = EmployeeConverter.toEmployeeEntity(employeeRequest);
        project = new Project(12, "Safe Guards","No description","Studds","studs@gmail.com",employee,23000,
                "23E",
                LocalDate.of(2021,04,18),
                7.9);
        notificationRequest = new NotificationRequest("client","Pranav","Approved");
        notification = EmployeeConverter.toNotification(notificationRequest);
        temporary.add(new Temporary(new Timestamp(System.currentTimeMillis())));
        requestList.add(new Request(23,project,LocalDate.now()));
        paymentModeRequest = new PaymentModeRequest("94353jksrw4",
                342334,
                 "Astik",
                "Online",
                "Paypal",
                new Timestamp(System.currentTimeMillis()),
                "Completed",
                "Hello all");
        clientPayment = new ClientPayment(1,
                requestList.get(0),
                "Approved",7000,"Approved","Approved","Approved","Approved","Approved",4 ,5, new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void test_ifEmployeeCreationIsSuccessful(){
        Mockito.when(employeeRepository.findCountOfEmployeeID(any())).thenReturn(0);
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        employee.setEmployeeID(employeeRequest.getTeamLeadID());
        Mockito.when(employeeRepository.findByEmployeeID(any())).thenReturn(employee);
        Mockito.when(projectRepository.findCountByTitle(any())).thenReturn(0);
        Project project = EmployeeConverter.toProjectEntity(employeeRequest,employee);
        Mockito.when(projectRepository.save(any(Project.class))).thenReturn(project);

        String result = addingDataService.addData(employeeRequest);

        assertThat(result,equalTo("Employee IN2012124 data has been added successfully"));
    }

    @Test
    void test_ifBandLevelIsSavedInDb(){
        Mockito.when(bandRepository.save(any(Band.class))).thenReturn(band);

        String result = addingDataService.addBand(band);

        assertThat(result,equalTo("Band7 Level Saved"));
    }

    @Test
    void test_ifClientPaymentIsSavedInDb(){
        Mockito.when(projectRepository.findByProjectId(any())).thenReturn(project);
        Mockito.when(requestRepository.save(any(Request.class))).thenReturn(null);
        Mockito.when(clientPaymentRepository.save(any(ClientPayment.class))).thenReturn(null);

        String result = addingDataService.createPayment(paymentRequest);

        assertThat(result, equalTo("Client Payment for project 12 has been added successfully"));
    }

    @Test
    void test_ifFinanceEmailIsSavedInDb(){
        Mockito.when(financeEmailRepository.save(any(FinanceEmails.class))).thenReturn(financeEmails);

        String result = addingDataService.addFinanceEmail(financeEmails);

        assertThat(result, equalTo("Email pranav.bansal@hashedin.com has been added to the db"));
    }

    @Test
    void test_ifNotificationIsAdded(){
        Mockito.when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        String result = addingDataService.addNotification(notificationRequest);

        assertThat(result,equalTo("Notification for Pranav has been saved successfully."));
    }

    @Test
    void test_ifTimestampIsSaved(){
        Mockito.when(temporaryRepository.findAll()).thenReturn(temporary);
        Mockito.when(temporaryRepository.save(any(Temporary.class))).thenReturn(temporary.get(0));

        String result = addingDataService.addTimeStamp();

        assertThat(result,equalTo("Timestamp"+ LocalDate.now().getMonth() +" has been saved"));
    }

    @Test
    void test_ifPaymentIsSaved(){
        Mockito.when(projectRepository.findByProjectId(any())).thenReturn(project);
        Mockito.when(requestRepository.findRequestByProjectID(any())).thenReturn(requestList);
        Mockito.when(clientPaymentRepository.findClientByRequestID(any(Integer.class))).thenReturn(clientPayment);
        Mockito.when(clientPaymentRepository.save(any(ClientPayment.class))).thenReturn(clientPayment);

        Payment payment = EmployeeConverter.toPaymentEntity(paymentModeRequest,requestList.get(0));
        Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        String result = addingDataService.addPayment(12,5,2021,paymentModeRequest);

        assertThat(result,equalTo("Payment info of Request 23 has been saved successfully"));
    }
}
