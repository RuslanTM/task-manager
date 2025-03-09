package com.petproject.taskmanager.data.entity;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import com.petproject.taskmanager.data.enums.UserActivityType;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Data
@Builder
public class UserActivityEntity {

    @PrimaryKeyColumn(name = "user_id", type = PARTITIONED)
    private String userId;

    @PrimaryKeyColumn(name = "event_time")
    private Instant eventTime;

    private String sessionId;

    private String activityName;

    private UserActivityType activityType;

    private String description;
}
