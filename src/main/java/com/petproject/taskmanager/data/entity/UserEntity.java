package com.petproject.taskmanager.data.entity;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import java.util.List;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UserEntity implements Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private List<String> createdTaskIds;

    private List<String> assignedTaskIds;
}
