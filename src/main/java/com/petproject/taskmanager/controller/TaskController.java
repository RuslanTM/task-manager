package com.petproject.taskmanager.controller;

import com.petproject.taskmanager.dto.TaskDto;
import com.petproject.taskmanager.exception.NotFoundException;
import com.petproject.taskmanager.service.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.createTask(taskDto));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/titles")
    public ResponseEntity<List<TaskDto>> getTaskTitlesWithoutIds() {
        return ResponseEntity.ok(taskService.getTaskTitlesWithoutIds());
    }

    @GetMapping("/lastExecuted")
    public ResponseEntity<TaskDto> getLastExecutedTask() {
        return ResponseEntity.ok(taskService.getLastExecutedTask());
    }

    @PostMapping("{taskId}/assign/{userId}")
    public ResponseEntity<TaskDto> assignTask(@PathVariable String taskId, @PathVariable String userId)
        throws NotFoundException {
        return ResponseEntity.ok(taskService.assignTask(taskId, userId));
    }
}
