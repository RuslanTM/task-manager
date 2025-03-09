package com.petproject.taskmanager.data.repository;

import com.petproject.taskmanager.data.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

}
