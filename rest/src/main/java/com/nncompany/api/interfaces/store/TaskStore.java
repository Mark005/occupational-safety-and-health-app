package com.nncompany.api.interfaces.store;

import com.nncompany.api.model.entities.Task;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskStore extends JpaRepository<Task, Integer> {

    Page<Task> findAllByStatusAndType(TaskStatus taskStatus, TaskType taskType, Pageable pageable);

    Page<Task> findAllByExecutorAndStatusAndType(User user,
                                                 TaskStatus taskStatus,
                                                 TaskType taskType,
                                                 Pageable pageable);
}
