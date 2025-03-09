package com.petproject.taskmanager.mapper;

import com.petproject.taskmanager.data.entity.ProjectEntity;
import com.petproject.taskmanager.data.entity.TaskEntity;
import com.petproject.taskmanager.data.entity.UserActivityEntity;
import com.petproject.taskmanager.data.entity.UserEntity;
import com.petproject.taskmanager.dto.ProjectDto;
import com.petproject.taskmanager.dto.TaskDto;
import com.petproject.taskmanager.dto.UserActivityDto;
import com.petproject.taskmanager.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    UserDto toUserDto(UserEntity userEntity);

    UserEntity toUserEntity(UserDto userDto);

    TaskDto toTaskDto(TaskEntity taskEntity);

    TaskEntity toTaskEntity(TaskDto taskDto);

    ProjectDto toProjectDto(ProjectEntity projectEntity);

    ProjectEntity toProjectEntity(ProjectDto projectDto);

    UserActivityDto toUserActivityDto(UserActivityEntity userActivityEntity);
}
