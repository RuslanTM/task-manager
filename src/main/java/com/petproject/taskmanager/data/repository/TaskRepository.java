package com.petproject.taskmanager.data.repository;

import com.petproject.taskmanager.data.entity.TaskEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TaskRepository extends MongoRepository<TaskEntity, String> {

    @Query(value = "{}",  fields = "{title: 1, _id:  0}")
    List<TaskEntity> findTitleAndExcludeId();
}
