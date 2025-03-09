package com.petproject.taskmanager.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;

import com.petproject.taskmanager.annotation.ActivityLog;
import com.petproject.taskmanager.data.entity.TaskEntity;
import com.petproject.taskmanager.data.enums.TaskStatusEnum;
import com.petproject.taskmanager.data.repository.TaskRepository;
import com.petproject.taskmanager.dto.TaskDto;
import com.petproject.taskmanager.exception.NotFoundException;
import com.petproject.taskmanager.mapper.TaskMapper;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final MongoTemplate mongoTemplate;
    private final UserService userService;

    private static final String CREATE_TASK = "CREATE_TASK";
    private static final String ASSIGN_TASK = "ASSIGN_TASK";

    @Transactional
    @ActivityLog(activityName = CREATE_TASK, description = "Create a new task")
    public TaskDto createTask(TaskDto taskDto) {
        TaskEntity taskEntity = taskMapper.toTaskEntity(taskDto);
        taskEntity.setStatus(TaskStatusEnum.DRAFT);
        taskEntity.setCreatedDate(Instant.now());
        TaskEntity newTaskEntity = taskRepository.save(taskEntity);
        return taskMapper.toTaskDto(newTaskEntity);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
            .stream()
            .map(taskMapper::toTaskDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public TaskDto getLastExecutedTask() {
        MatchOperation finishedTasks = Aggregation.match(new Criteria("executionDate").ne(null));
        SortOperation sortByExecutionDate = Aggregation.sort(Sort.by(Direction.DESC, "executionDate"));
        LimitOperation limitToTheFirstTask = limit(1);
        Aggregation aggregation = Aggregation.newAggregation(finishedTasks, sortByExecutionDate, limitToTheFirstTask);

        AggregationResults<TaskEntity> task = mongoTemplate.aggregate(aggregation, "tasks", TaskEntity.class);
        return taskMapper.toTaskDto(task.getUniqueMappedResult());
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTaskTitlesWithoutIds() {
        List<TaskEntity> titlesAndExcludedIds = taskRepository.findTitleAndExcludeId();
        return titlesAndExcludedIds.stream()
            .map(taskMapper::toTaskDto)
            .toList();
    }

    @Transactional
    @ActivityLog(activityName = ASSIGN_TASK, description = "Assign a task")
    public TaskDto assignTask(String taskId, String userId) throws NotFoundException {
        TaskEntity taskEntity = taskRepository.findById(taskId)
            .orElseThrow(() -> new NotFoundException("Task not found"));

        var user = userService.findById(userId);

        taskEntity.setExecutorId(user.getId());
        taskEntity.setStatus(TaskStatusEnum.IN_PROGRESS);
        return updateTask(taskMapper.toTaskDto(taskEntity));
    }

    @Transactional
    public TaskDto updateTask(TaskDto taskDto) {
        TaskEntity taskEntity = taskMapper.toTaskEntity(taskDto);
        TaskEntity updatedTaskEntity = taskRepository.save(taskEntity);
        return taskMapper.toTaskDto(updatedTaskEntity);
    }
}
