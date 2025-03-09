package com.petproject.taskmanager.service;

import com.petproject.taskmanager.data.entity.ProjectEntity;
import com.petproject.taskmanager.data.repository.ProjectRepository;
import com.petproject.taskmanager.dto.ProjectDto;
import com.petproject.taskmanager.mapper.TaskMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll()
            .stream()
            .map(taskMapper::toProjectDto)
            .toList();
    }

    @Transactional
    public ProjectDto save(ProjectDto projectDto) {
        ProjectEntity projectEntity = taskMapper.toProjectEntity(projectDto);
        ProjectEntity newProjectEntity = projectRepository.save(projectEntity);
        return taskMapper.toProjectDto(newProjectEntity);
    }
}
