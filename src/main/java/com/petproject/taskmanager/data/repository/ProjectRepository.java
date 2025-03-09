package com.petproject.taskmanager.data.repository;

import com.petproject.taskmanager.data.entity.ProjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<ProjectEntity, String> {

}
