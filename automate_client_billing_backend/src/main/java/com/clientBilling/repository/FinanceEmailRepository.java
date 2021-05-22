package com.clientBilling.repository;

import com.clientBilling.entity.FinanceEmails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("FinanceEmailRepo")
public interface FinanceEmailRepository extends CrudRepository<FinanceEmails, Integer> {
    @Query("select emails from FinanceEmails emails where email.email = :id")
    FinanceEmails findEmailById(@Param("id") String id);
}
