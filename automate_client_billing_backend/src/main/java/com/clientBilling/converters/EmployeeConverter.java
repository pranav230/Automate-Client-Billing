package com.clientBilling.converters;


import com.clientBilling.entity.*;
import com.clientBilling.request.EmployeeRequest;
import com.clientBilling.request.NotificationRequest;
import com.clientBilling.request.PaymentModeRequest;
import com.clientBilling.response.BilldetailsResponse;
import com.clientBilling.response.EmployeeBilldetailsResponse;
import com.clientBilling.response.HomePageResponse;
import com.clientBilling.response.TransactionResponse;

import java.sql.Timestamp;
import java.util.List;

public class EmployeeConverter {
    public static Employee toEmployeeEntity(EmployeeRequest employeeRequest){
        return new Employee(
                employeeRequest.getEmployeeID(),
                employeeRequest.getEmployeeName(),
                employeeRequest.getDesignation(),
                employeeRequest.getDepartment(),
                employeeRequest.getEmail(),
                employeeRequest.getBand()
        );
    }

    public static Project toProjectEntity(EmployeeRequest employeeRequest, Employee teamLead){
        return new Project(
                null,
                employeeRequest.getProjectTitle(),
                employeeRequest.getProjectDesc(),
                employeeRequest.getClientName(),
                employeeRequest.getClientEmail(),
                teamLead,
                employeeRequest.getPoBalance(),
                employeeRequest.getPoNumber(),
                employeeRequest.getPoExpiry(),
                employeeRequest.getGst()
        );
    }

    public static EmployeeProject toEmployeeProjectEntity(EmployeeRequest employeeRequest, Employee employee, Project project, Integer ratePerHour){
        return new EmployeeProject(
                null,
                employee,
                project,
                employeeRequest.getStartDate(),
                employeeRequest.getEndDate(),
                employeeRequest.getLeave(),
                employeeRequest.getCurrentMonthLeaves(),
                employeeRequest.getTotalLeaves(),
                employeeRequest.getMonthOFYear(),
                employeeRequest.getPercent(),
                ratePerHour,
                0,0
        );
    }

    public static EmployeeBilldetailsResponse toEmployeeBillDetailsResponse(EmployeeProject emp){
        return new EmployeeBilldetailsResponse(
                emp.getEmployee().getEmployeeID(),
                emp.getEmployee().getEmployeeName(),
                emp.getEmployee().getDesignation(),
                emp.getLeaves(),
                emp.getHoursBilled(),
                emp.getRatePerHour(),
                emp.getStartDate(),
                emp.getEndDate(),
                emp.getIndividualPay()
        );
    }

    public static BilldetailsResponse toBillDetailsResponse(Project pro, int totalSalary, int totalEmployee, List<EmployeeBilldetailsResponse> employeeBilldetailsResponseList, String leadStatus){
        return new BilldetailsResponse(
          pro.getProjectID(),
          pro.getProjectTitle(),
          pro.getClientName(),
          totalEmployee,
          totalSalary, leadStatus,
          employeeBilldetailsResponseList
        );
    }

    public static HomePageResponse toHomePageResponse(Project pro,List<String> paymentStatus,Integer totalEmployees){
        return new HomePageResponse(
                pro.getProjectTitle(),
                pro.getClientName(),
                totalEmployees,
                paymentStatus
        );
    }

    public static Notification toNotification(NotificationRequest notificationRequest){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestamp = new Timestamp(timestamp.getTime() + (1000*60*60*5 + 1000*60*30));
        return new Notification(null,
                timestamp,
                notificationRequest.getType(),
                notificationRequest.getName(),
                notificationRequest.getStatus()
        );
    }

    public static Payment toPaymentEntity(PaymentModeRequest paymentMode, Request request){
        return new Payment(
                paymentMode.getId(),
                paymentMode.getAmount(),
                paymentMode.getPayeeName(),
                paymentMode.getMode(),
                paymentMode.getModeType(),
                paymentMode.getTimestamp(),
                request
        );
    }

    public static TransactionResponse toTransactionResponse(ClientPayment clientPayment,Payment payment){
        return new TransactionResponse(payment.getId(),
                payment.getAmount(),
                payment.getPayeeName(),
                payment.getTimestamp(),
                payment.getMode(),
                payment.getModeType(),
                clientPayment.getClientFinanceComment()
        );
    }
}
