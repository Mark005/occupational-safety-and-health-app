package com.nncompany.impl.service;

import com.nncompany.api.interfaces.services.UserCredentialsService;
import com.nncompany.api.interfaces.store.UserCredentialsStore;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserCredentials;
import com.nncompany.api.model.enums.Gender;
import com.nncompany.api.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserCredentialsServiceImpl implements UserCredentialsService {

    @Autowired
    private UserCredentialsStore userCredentialsStore;

    @Override
    public boolean isUserExistWithCurrentLoginAndPass(String login, String pass) {
        return userCredentialsStore.findByLoginAndPass(login, pass).isPresent();
    }

    @Override
    public UserCredentials getByLogin(String login) {
        return userCredentialsStore
                .findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Boolean checkLogin(String login) {
        return userCredentialsStore
                .findByLogin(login)
                .isPresent();
    }

    @Override
    public void save(UserCredentials userCredentials) {
        userCredentialsStore.save(userCredentials);
    }

    @Override
    public void update(UserCredentials userCredentials) {
        userCredentialsStore.save(userCredentials);
    }
}
