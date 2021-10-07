package com.nncompany.impl.service.task;

import com.nncompany.api.interfaces.services.task.TaskForRoleGetterService;
import com.nncompany.api.interfaces.store.TaskStore;
import com.nncompany.api.model.entities.Task;
import com.nncompany.api.model.enums.Role;
import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import com.nncompany.impl.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TasksForUserGetterService implements TaskForRoleGetterService {

    @Autowired
    TaskStore taskStore;

    @Override
    public Page<Task> getTasks(TaskType type, TaskStatus status, Pageable pageable) {
        return taskStore.findAllByExecutorAndStatusAndType(SecurityUtils.getCurrentUser(), status, type, pageable);
    }

    @Override
    public Role getRole() {
        return Role.USER;
    }
}
