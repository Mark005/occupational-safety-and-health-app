package com.nncompany.api.dto.task;

import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskChangeStatusDto {
    private Integer id;
    private TaskStatus taskStatus;
}
