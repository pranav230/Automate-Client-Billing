package com.clientBilling.repository;

import com.clientBilling.entity.EmployeeProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository("EmployeeProjectRepo")
public interface EmployeeProjectRepository extends CrudRepository<EmployeeProject, Integer> {

    @Query("select employeeProject from EmployeeProject  employeeProject where employeeProject.project.projectID = :id")
    List<EmployeeProject> findByProjectID(@Param("id") Integer projectID);

    @Query("select employeeProject from EmployeeProject  employeeProject where employeeProject.serialID = :id")
    EmployeeProject findBySerialId(@Param("id") Integer serialID);
}
