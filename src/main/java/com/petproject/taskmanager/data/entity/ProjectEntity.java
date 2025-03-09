package com.petproject.taskmanager.data.entity;

import com.petproject.taskmanager.data.enums.ProjectStatusEnum;
import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "projects")
public class ProjectEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private ProjectStatusEnum status;
}
