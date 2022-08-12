package com.stremel.usersservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stremel.usersservice.model.User;

import java.sql.Timestamp;

public class UserDTO {
    private String id;
    private Timestamp created;
    private Timestamp modified;
    private Timestamp lastLogin;
    private String token;
    private boolean isActive;

    public UserDTO() {}

    public UserDTO(final String id,
                   final Timestamp created,
                   final Timestamp modified,
                   final Timestamp lastLogin,
                   final String token,
                   final boolean isActive) {
        this.id = id;
        this.created = created;
        this.modified = modified;
        this.lastLogin = lastLogin;
        this.token = token;
        this.isActive = isActive;
    }

    public UserDTO(final User user) {
        this.id = user.getId();
        this.created = user.getCreated();
        this.modified = user.getModified();
        this.lastLogin = user.getLastLogin();
        this.token = user.getToken();
        this.isActive = user.isActive();
    }

    public String getId() {
        return id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public String getToken() {
        return token;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }
}
