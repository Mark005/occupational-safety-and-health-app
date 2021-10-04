package com.nncompany.impl.service;

import com.nncompany.api.interfaces.services.TaskService;
import com.nncompany.api.interfaces.store.TaskStore;
import com.nncompany.api.model.entities.Task;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskStore taskStore;

    @Override
    public Task getById(int id) {
        return taskStore.getById(id);
    }

    @Override
    public List<Task> getWithPagination(Integer page, Integer pageSize) {
        return taskStore
                .findAll(PageRequest.of(page, pageSize))
                .getContent();
    }

    @Override
    public List<Task> getUsersTasks(User user, TaskStatus taskStatus, TaskType taskType) {
        return taskStore
                .findAllByExecutorAndStatusAndType(user, taskStatus, taskType)
                .getContent();
    }

    @Override
    public void save(Task task) {
        taskStore.save(task);
    }

    @Override
    public void update(Task task) {
        taskStore.save(task);
    }

    @Override
    public void delete(Task task) {
        taskStore.delete(task);
    }
}
