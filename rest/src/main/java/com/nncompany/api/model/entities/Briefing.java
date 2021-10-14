package com.nncompany.api.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "briefing")
public class Briefing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer intervalInMonths;

    @OneToMany(mappedBy = "briefing")
    private List<UserBriefing> userBriefingList;

    @Override
    public boolean equals(Object obj) {
        Briefing m = (Briefing) obj;
        return this.id.equals(m.getId());
    }
}
