package com.clientBilling.repository;

import com.clientBilling.entity.ClientPayment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("ClientPaymentRepo")
public interface ClientPaymentRepository extends CrudRepository<ClientPayment, Integer> {

    @Query("select client.teamLeadStatus from ClientPayment client where client.request.requestId = :id")
    String findByRequestID(@Param("id") Integer requestID);

    @Query("select client.clientStatus from ClientPayment client where client.request.requestId = :id")
    String findClientStatusByRequestID(@Param("id") Integer requestID);

    @Query("select client.teamLeadStatus from ClientPayment client where client.request.requestId = :id")
    String findTeamLeadStatusByRequestID(@Param("id") Integer requestID);

    @Query("select client.teamLeadReminder from ClientPayment client where client.request.requestId = :id")
    Integer findTeamReminderByRequestID(@Param("id") Integer requestID);

    @Query("select client from ClientPayment client where client.request.requestId = :id")
    ClientPayment findClientByRequestID(@Param("id") Integer requestID);
}
