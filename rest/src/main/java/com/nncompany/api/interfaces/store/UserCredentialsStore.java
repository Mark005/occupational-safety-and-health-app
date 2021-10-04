package com.nncompany.api.interfaces.store;

import com.nncompany.api.model.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserCredentialsStore extends JpaRepository<UserCredentials, Integer> {
    Optional<UserCredentials> findByLoginAndPass(String login, String pass);

    Optional<UserCredentials> findByLogin(String login);
}
