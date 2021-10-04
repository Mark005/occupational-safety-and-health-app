package com.nncompany.api.model.entities;

import com.nncompany.api.model.enums.Gender;
import com.nncompany.api.model.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "certificate_number")
    private Integer certificateNumber;

    private String name;

    private String surname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String profession;

    @Column(name = "date_employment")
    private Date dateEmployment;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    private List<UserBriefing> userBriefingList;

/*    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private UserCredentials userCredentials;*/

    @Override
    public boolean equals(Object obj) {
        User m = (User) obj;
        return this.id.equals(m.getId());
    }
}
