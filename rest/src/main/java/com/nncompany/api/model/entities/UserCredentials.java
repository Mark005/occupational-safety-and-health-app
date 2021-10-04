package com.nncompany.api.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_creds")
public class UserCredentials {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "login")
    private String login;

    @Column(name = "pass")
    private String pass;

    @OneToOne()
    @JoinColumn(name = "user_fk")
    private User user;

    public UserCredentials(String login, String pass){
        this.login = login;
        this.pass = pass;
    }

    public static UserDetails toUserDetails(UserCredentials userCredentials) {
        return new org.springframework.security.core.userdetails.User(
                userCredentials.getLogin(),
                userCredentials.getPass(),
                userCredentials.getUser().getRole().getAuthorities());
    }
}
