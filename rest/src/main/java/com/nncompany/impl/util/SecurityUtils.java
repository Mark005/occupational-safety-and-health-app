package com.nncompany.impl.util;

import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserCredentials;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtils {

    public static User getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        throw new RuntimeException("Getting user error");
    }

    public static UserDetails toUserDetails(UserCredentials userCredentials) {
        return new org.springframework.security.core.userdetails.User(
                userCredentials.getLogin(),
                userCredentials.getPass(),
                userCredentials.getUser().getRole().getAuthorities());
    }
}
