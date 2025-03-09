package com.petproject.taskmanager.data.entity;

import com.petproject.taskmanager.data.enums.TaskStatusEnum;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tasks")
public class TaskEntity {

    @Id
    private String id;

    private String title;

    private String description;

    private LocalDate dueDate;

    private Instant createdDate;

    private Instant executionDate;

    private String authorId;

    private String executorId;

    private TaskStatusEnum status;
}
