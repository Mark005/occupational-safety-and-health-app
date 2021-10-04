package com.nncompany.api.model.enums;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {
    ADMIN(Set.of(Permission.values())),
    USER(Set.of(Permission.READ_USERS, Permission.READ_BRIEFINGS));

    private Set<Permission> permissions;

    Role(Set<Permission> userPermissions) {
        this.permissions = userPermissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
