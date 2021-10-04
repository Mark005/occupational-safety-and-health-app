package com.nncompany.rest.security.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface TokenProvider {

    void regenerateKey();

    String getToken(String username);

    boolean validateToken(String token);

    Authentication getAuthentication(String token);

    String getUsername(String token);

    String resolveToken(HttpServletRequest request);
}
