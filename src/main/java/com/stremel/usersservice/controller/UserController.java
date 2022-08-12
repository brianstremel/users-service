package com.stremel.usersservice.controller;

import com.stremel.usersservice.dto.UserCreationDTO;
import com.stremel.usersservice.dto.UserDTO;
import com.stremel.usersservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> updateUser(@RequestHeader("token") final String token,
                                              @RequestBody final UserCreationDTO userCreationDTO) {
        final UserDTO userDTO = userService.updateUser(userCreationDTO, token);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDTO);
    }

    @DeleteMapping
    public ResponseEntity<UserDTO> deleteUser(@RequestHeader("token") final String token) {
        final UserDTO userDTO = userService.deleteUser(token);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("token", "")
                .body(userDTO);
    }

}
