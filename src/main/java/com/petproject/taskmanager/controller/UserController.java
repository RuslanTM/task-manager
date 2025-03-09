package com.petproject.taskmanager.controller;

import com.mongodb.MongoWriteException;
import com.petproject.taskmanager.data.entity.UserEntity;
import com.petproject.taskmanager.dto.UserDto;
import com.petproject.taskmanager.exception.NotFoundException;
import com.petproject.taskmanager.mapper.TaskMapper;
import com.petproject.taskmanager.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) throws NotFoundException {
        UserEntity userEntity = userService.findById(id);
        UserDto userDto = taskMapper.toUserDto(userEntity);
        return ResponseEntity.ok(userDto);

    }


    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {

        //TODO: Add a data validation
        try {
            UserEntity newUser = userService.save(user);
            return ResponseEntity.ok(taskMapper.toUserDto(newUser));
        } catch (MongoWriteException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
