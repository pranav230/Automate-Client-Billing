package com.clientBilling.repository;

import com.clientBilling.entity.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RequestRepo")
public interface RequestRepository extends CrudRepository<Request,Integer> {

    @Query("select request.requestId from Request request where request.project.projectID = :id" )
    List<Integer> findByProjectID(@Param("id") Integer projectID);

    @Query("select request.project.projectID from Request request where request.requestId = :id")
    Integer findProjectByRequestID(@Param("id") Integer requestID);

    @Query("select request from Request request where request.project.projectID = :id" )
    List<Request> findRequestByProjectID(@Param("id") Integer projectID);
}
