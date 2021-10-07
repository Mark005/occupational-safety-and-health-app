package com.nncompany.api.interfaces.services.task;

import com.nncompany.api.model.entities.Task;
import com.nncompany.api.model.enums.Role;
import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskForRoleGetterService {

    Page<Task> getTasks(TaskType type, TaskStatus status, Pageable pageable);

    Role getRole();
}
