package com.nncompany.api.model.entities;

import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private Date deadline;

    @ManyToOne()
    @JoinColumn(name = "creator")
    private User creator;

    @ManyToOne()
    @JoinColumn(name = "executor")
    private User executor;
}
