package com.nncompany.api.interfaces.services;

import com.nncompany.api.model.entities.Task;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;

import java.util.List;

public interface TaskService {

    Task getById(int id);

    List<Task> getWithPagination(Integer offset, Integer limit);

    List<Task> getUsersTasks(User user, TaskStatus taskSatus, TaskType taskType);

    void save(Task task);

    void update(Task task);

    void delete(Task task);
}
