package com.clientBilling;

import com.clientBilling.entity.*;
import com.clientBilling.repository.*;
import com.clientBilling.response.EmployeeBilldetailsResponse;
import com.clientBilling.response.ProjectDetails;
import com.clientBilling.service.EmployeeService;
import com.clientBilling.service.UpdatingService;
import org.checkerframework.checker.units.qual.C;
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

@SpringBootTest
public class EmployeeServiceTest {

    private ProjectDetails projectDetails;
    private List<EmployeeBilldetailsResponse> empList;
    private List<EmployeeProject> employeeProjectList;
    private EmployeeService employeeService;
    private UpdatingService updatingService;
    private Project project;
    private Employee employee;
    private ClientPayment clientPayment;

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
    private NotificationRepository notificationRepository;

    @Mock
    private TemporaryRepository temporaryRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private FinanceEmailRepository financeEmailRepository;

    @BeforeEach
    void setup(){
        employeeService = new EmployeeService(projectRepository,
                bandRepository,
                employeeProjectRepository,
                requestRepository,
                clientPaymentRepository, notificationRepository, temporaryRepository,updatingService,paymentRepository,financeEmailRepository);
        empList = new ArrayList<>();
        empList.add(new EmployeeBilldetailsResponse("IN2012124",
                "Pranav",
                "SDE-II",
                5,
                300,1000,LocalDate.of(2021,1,5),LocalDate.of(2021,1,31),
                5000));
        projectDetails = new ProjectDetails("Tata",
                "The Client Bill",
                "IN2012124",
                "Pranav",
                "Approved",
                "Approved",
                1000,
                0,
                0,
                "Approved",
                "hi",
                "Approved",
                3,"Online",
                empList
        );
        employee = new Employee("IN2012124","Pranav","SDE-II","Delivery","pranav.bansal@hashedin.com","Band8");
        project = new Project(9,"The Client Bill","","Tata","tata@gmail.com",employee,23000,
                "23E",
                LocalDate.of(2021,04,18),
                7.9);
        clientPayment = new ClientPayment(1,
                new Request(8,project,LocalDate.of(2001,1,20)),
                "Approved",7000,"Approved","Approved","Approved","Approved","Approved",4 ,5, new Timestamp(System.currentTimeMillis()));
        employeeProjectList = new ArrayList<>();
        employeeProjectList.add(new EmployeeProject(12,employee,project, LocalDate.of(2021,1,5),
                LocalDate.of(2021,1,31),5,2,3,LocalDate.of(2001,3,20),50.0,1000,300,5000));
    }

    @Test
    void test_ifProjectDetailsAreSent(){
        Mockito.when(projectRepository.findByProjectId(any())).thenReturn(project);
        Mockito.when(clientPaymentRepository.findClientByRequestID(any())).thenReturn(clientPayment);
        Mockito.when(employeeProjectRepository.findByProjectID(any())).thenReturn(employeeProjectList);
        Mockito.when(bandRepository.findRateByBandLevel(any())).thenReturn(60000);

        ProjectDetails result = employeeService.projectDetails(9,3,2021 );

        assertThat(result.getClientName(),equalTo(projectDetails.getClientName()));
        assertThat(result.getTeamLeadName(),equalTo(projectDetails.getTeamLeadName()));
        assertThat(result.getEmployees(), equalTo(projectDetails.getEmployees()));
    }
}
