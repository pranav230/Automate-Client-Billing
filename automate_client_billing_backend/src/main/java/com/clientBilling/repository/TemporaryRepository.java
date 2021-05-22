package com.clientBilling.repository;

import com.clientBilling.entity.Temporary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface TemporaryRepository extends CrudRepository<Temporary, Timestamp> {
}
