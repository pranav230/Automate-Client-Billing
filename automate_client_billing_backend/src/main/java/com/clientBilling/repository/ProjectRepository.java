package com.clientBilling.repository;

import com.clientBilling.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("ProjectRepo")
public interface ProjectRepository extends CrudRepository<Project,Integer> {
    @Query("select count(project) from Project project where project.projectTitle = :title")
    Integer findCountByTitle(@Param("title") String title);

    @Query("select project from Project project where project.projectTitle = :title")
    Project findByTitle(@Param("title") String title);

    @Query("select project from Project project where project.projectID = :id")
    Project findByProjectId(@Param("id") Integer id);
}
