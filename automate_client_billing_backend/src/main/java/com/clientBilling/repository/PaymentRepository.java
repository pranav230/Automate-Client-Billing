package com.clientBilling.repository;

import com.clientBilling.entity.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment,String> {

    @Query("select payment.mode from Payment payment where payment.request.requestId = :id")
    String findModeByRequestID(@Param("id") Integer id);

    @Query("select payment from Payment payment where payment.request.requestId = :id")
    Payment findPaymentByRequestID(@Param("id") Integer id);
}
