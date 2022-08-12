package com.stremel.usersservice.controller;

import com.stremel.usersservice.dto.LoginDTO;
import com.stremel.usersservice.dto.UserCreationDTO;
import com.stremel.usersservice.dto.UserDTO;
import com.stremel.usersservice.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/authentication")
@Tag(name = "/v1/authentication")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(final UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Logins user")
    @ApiResponse(responseCode = "200", description = "Returns metadata about the logged user")
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Parameter(description = "Login data") @RequestBody final LoginDTO loginDTO) {
        final UserDTO userDTO = userService.login(loginDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("token", userDTO.getToken())
                .body(userService.login(loginDTO));
    }

    @Operation(summary = "Logouts user")
    @ApiResponse(responseCode = "200")
    @PostMapping("/logout")
    public ResponseEntity<Object> logout() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("token", "")
                .build();
    }

    @Operation(summary = "Creates a user with the given data")
    @ApiResponse(responseCode = "201", description = "Returns metadata about the created user")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @Parameter(description = "Creation data for user") @RequestBody @Valid
            final UserCreationDTO user) {
        final UserDTO userDTO = userService.createUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("token", userDTO.getToken())
                .body(userDTO);
    }

}
