package com.komanrudden.model.data;

import io.quarkus.security.identity.SecurityIdentity;
import lombok.Getter;

@Getter
public class User {

    private final String userName;

    public User(SecurityIdentity identity) {
        this.userName = identity.getPrincipal().getName();
    }
}