package com.nncompany.api.interfaces.services;

import com.nncompany.api.dto.task.TaskChangeStatusDto;
import com.nncompany.api.dto.task.TaskCreateDto;
import com.nncompany.api.model.entities.Task;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {


    Task findById(Integer id);

    Page<Task> findAllAvailableTasksForCurrentUser(TaskType type, TaskStatus status, Integer page, Integer pageSize);

    Task create(TaskCreateDto taskCreateDto);

    Task changeTaskStatus(TaskChangeStatusDto taskDto);

    Task update(Task task);

    void delete(Integer id);
}
