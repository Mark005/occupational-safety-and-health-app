package com.nncompany.api.interfaces.services;

import com.nncompany.api.model.entities.UserCredentials;

import java.util.List;
import java.util.Optional;

public interface UserCredentialsService {

    void save(UserCredentials userCredentials);

    boolean isUserExistWithCurrentLoginAndPass(String login, String pass);

    UserCredentials getByLogin(String login);

    Boolean checkLogin(String login);

    void update(UserCredentials userCredentials);
}
