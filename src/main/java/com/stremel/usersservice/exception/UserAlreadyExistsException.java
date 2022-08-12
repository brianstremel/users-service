package com.stremel.usersservice.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends HttpException {

    public UserAlreadyExistsException() {
        super("El correo ya registrado.", HttpStatus.CONFLICT);
    }
}
