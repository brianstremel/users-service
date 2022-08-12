package com.stremel.usersservice.dto;

public class LoginDTO {
    private String email;
    private String password;
    private String token;

    public LoginDTO() {
    }

    public LoginDTO(final String username, final String password, final String token) {
        this.email = username;
        this.password = password;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
}
