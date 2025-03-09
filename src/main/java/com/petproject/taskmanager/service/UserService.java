package com.petproject.taskmanager.service;

import com.petproject.taskmanager.data.entity.UserEntity;
import com.petproject.taskmanager.data.repository.UserRepository;
import com.petproject.taskmanager.dto.UserDto;
import com.petproject.taskmanager.exception.NotFoundException;
import com.petproject.taskmanager.mapper.TaskMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Transactional
    @CachePut(value = "users", key = "#result.id")
    public UserEntity save(UserDto user) {
        UserEntity userEntity = taskMapper.toUserEntity(user);
        return userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "users", key = "#id")
    public UserEntity findById(String id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
            .map(taskMapper::toUserDto)
            .toList();
    }

    @Transactional
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(String id) throws NotFoundException {
        var user = findById(id);
        userRepository.deleteById(user.getId());
    }
}
