package com.nncompany.impl.util;

import com.nncompany.api.model.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    public static User getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        throw new RuntimeException("Getting user error");
    }
}
