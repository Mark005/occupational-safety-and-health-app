package com.nncompany.api.model.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_has_briefing")
public class UserBriefing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Briefing briefing;

    private LocalDateTime lastDate;
}
