package com.clientBilling.repository;

import com.clientBilling.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("EmployeeRepo")
public interface EmployeeRepository extends CrudRepository<Employee,String> {
    @Query("select count(employee) from Employee employee where employee.employeeID = :id")
    Integer findCountOfEmployeeID(@Param("id") String id);

    @Query("select employee from Employee employee where employee.employeeID = :id")
    Employee findByEmployeeID(@Param("id") String id);
}
