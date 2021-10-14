package com.nncompany.api.dto.task;

import com.nncompany.api.model.enums.TaskType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskCreateDto {
    private String name;
    private TaskType type;
    private LocalDateTime deadline;
    private Integer executorId;
}
