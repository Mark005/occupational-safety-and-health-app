package com.nncompany.impl.service;

import com.nncompany.api.dto.task.TaskChangeStatusDto;
import com.nncompany.api.dto.task.TaskCreateDto;
import com.nncompany.api.interfaces.services.TaskService;
import com.nncompany.api.interfaces.services.task.TaskForRoleGetterService;
import com.nncompany.api.interfaces.store.TaskStore;
import com.nncompany.api.interfaces.store.UserStore;
import com.nncompany.api.model.entities.Task;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.enums.Role;
import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import com.nncompany.impl.exception.EntityNotFoundException;
import com.nncompany.impl.exception.PermissionDeniedException;
import com.nncompany.impl.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskStore taskStore;

    @Autowired
    UserStore userStore;

    @Autowired
    Map<Role, TaskForRoleGetterService> taskByRoleGetterServiceMap;

    @Override
    public Task findById(Integer id) {
        Task task = taskStore
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not fount"));

        User currentUser = SecurityUtils.getCurrentUser();

        if (Role.ADMIN == currentUser.getRole()) {
            return task;
        }

        if (!Objects.equals(currentUser, task.getCreator()) ||
                !Objects.equals(currentUser, task.getExecutor())) {
            throw new PermissionDeniedException("You are not creator or executor");
        }
        return task;
    }

    @Override
    public Page<Task> findAllAvailableTasksForCurrentUser(TaskType type,
                                                          TaskStatus status,
                                                          Integer page,
                                                          Integer pageSize) {
        Role currentUserRole = SecurityUtils.getCurrentUser().getRole();

        return taskByRoleGetterServiceMap.get(currentUserRole).getTasks(type, status, PageRequest.of(page, pageSize));
    }

    @Override
    public Task create(TaskCreateDto taskDto) {
        Task task = Task.builder()
                .name(taskDto.getName())
                .type(taskDto.getType())
                .status(TaskStatus.OPEN)
                .deadline(taskDto.getDeadline())
                .creator(SecurityUtils.getCurrentUser())
                .executor(userStore.getById(taskDto.getExecutorId()))
                .build();
        return taskStore.save(task);
    }

    @Override
    public Task changeTaskStatus(TaskChangeStatusDto taskDto) {
        Task task = taskStore
                .findById(taskDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Task not fount"));

        if (!Objects.equals(task.getCreator(), SecurityUtils.getCurrentUser())) {
            throw new PermissionDeniedException("You are not creator of this task");
        }

        task.setStatus(taskDto.getTaskStatus());
        return taskStore.save(task);
    }

    @Override
    public Task update(Task task) {
        return taskStore.save(task);
    }

    @Override
    public void delete(Integer id) {
        taskStore.deleteById(id);
    }
}
