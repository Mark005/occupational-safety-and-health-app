package com.nncompany.api.model.entities;

import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDateTime deadline;

    @ManyToOne
    private User creator;

    @ManyToOne
    private User executor;
}
