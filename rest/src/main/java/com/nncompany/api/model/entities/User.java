package com.nncompany.api.model.entities;

import com.nncompany.api.model.enums.Gender;
import com.nncompany.api.model.enums.Role;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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
    @ToString.Exclude
    private List<UserBriefing> userBriefingList;

/*    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private UserCredentials userCredentials;*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
