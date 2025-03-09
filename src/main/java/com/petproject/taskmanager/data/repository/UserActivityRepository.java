package com.petproject.taskmanager.data.repository;

import com.petproject.taskmanager.data.entity.UserActivityEntity;
import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserActivityRepository extends CassandraRepository<UserActivityEntity, String> {
    public List<UserActivityEntity> findByUserIdOrderByEventTimeAsc(String userId);
}
