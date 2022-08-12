package com.stremel.usersservice.service;

import com.stremel.usersservice.dto.LoginDTO;
import com.stremel.usersservice.dto.UserCreationDTO;
import com.stremel.usersservice.dto.UserDTO;

public interface UserService {

    UserDTO createUser(UserCreationDTO user);
    UserDTO updateUser( UserCreationDTO userDto, String token);
    UserDTO login(LoginDTO loginDTO);
    UserDTO deleteUser(String token);
 }
