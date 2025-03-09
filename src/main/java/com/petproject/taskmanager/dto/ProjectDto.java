package com.petproject.taskmanager.dto;

import com.petproject.taskmanager.data.enums.ProjectStatusEnum;

public record ProjectDto(
    String name,
    ProjectStatusEnum status) {
}
