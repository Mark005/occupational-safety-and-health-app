package com.nncompany.api.model.enums;

import lombok.Getter;

@Getter
public enum Permission {
    ADD_USERS("users:add"),
    READ_USERS("users:read"),

    ADD_BRIEFINGS("briefings:add"),
    EDIT_BRIEFINGS("briefings:edit"),
    READ_BRIEFINGS("briefings:read");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
