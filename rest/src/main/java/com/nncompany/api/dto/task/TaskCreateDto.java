package com.nncompany.api.dto.task;

import com.nncompany.api.model.enums.TaskType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskCreateDto {
    private String name;
    private TaskType type;
    private Date deadline;
    private Integer executorId;
}
