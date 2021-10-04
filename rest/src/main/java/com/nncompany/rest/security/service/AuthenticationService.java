package com.nncompany.rest.security.service;

public interface AuthenticationService {
    String getTokenByLoginAndPass(String login, String pass);
}
