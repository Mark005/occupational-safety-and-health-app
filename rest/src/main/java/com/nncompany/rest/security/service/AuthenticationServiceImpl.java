package com.nncompany.rest.security.service;

import com.nncompany.api.interfaces.services.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserCredentialsService userCredentialsService;

    @Autowired
    TokenProvider tokenProvider;

    @Override
    public String getTokenByLoginAndPass(String login, String pass) {
        if (userCredentialsService.isUserExistWithCurrentLoginAndPass(login, pass)) {
            return tokenProvider.getToken(login);
        } else {
            throw new BadCredentialsException("Invalid pair of login and password");
        }
    }
}
