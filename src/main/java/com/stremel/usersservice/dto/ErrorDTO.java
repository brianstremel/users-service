package com.stremel.usersservice.dto;

public class ErrorDTO {
    private final String message;

    public ErrorDTO(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
