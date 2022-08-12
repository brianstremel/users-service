package com.stremel.usersservice.service;

import com.stremel.usersservice.dao.UserDAO;
import com.stremel.usersservice.dto.LoginDTO;
import com.stremel.usersservice.dto.UserCreationDTO;
import com.stremel.usersservice.dto.UserDTO;
import com.stremel.usersservice.exception.NotFoundException;
import com.stremel.usersservice.exception.UnauthorizedException;
import com.stremel.usersservice.exception.UserAlreadyExistsException;
import com.stremel.usersservice.model.User;
import com.stremel.usersservice.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {

    final private UserDAO userDAO;
    final private SecurityService securityService;
    final private RegexUtils regexUtils;

    @Autowired
    public UserServiceImpl(final UserDAO userDAO,
                           final SecurityService securityService,
                           final RegexUtils regexUtils) {
        this.userDAO = userDAO;
        this.securityService = securityService;
        this.regexUtils = regexUtils;
    }

    @Override
    public UserDTO createUser(final UserCreationDTO userDto) {
        try {
            regexUtils.validatePassword(userDto.getPassword());
            final String userId = securityService.generateUUID();
            User user = new User(userId,
                    userDto,
                    securityService.hashPassword(userDto.getPassword()),
                    securityService.generateUserToken(userId, userDto.getEmail()));
            userDAO.save(user);
            return new UserDTO(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public UserDTO updateUser(final UserCreationDTO userDto, final String token) {
        try {
            regexUtils.validatePassword(userDto.getPassword());
            final String userId = securityService.validateUserToken(token).getKeyId();
            User user = new User(userId,
                    userDto,
                    securityService.hashPassword(userDto.getPassword()),
                    token);
            user.setModified(new Timestamp(System.currentTimeMillis()));
            userDAO.save(user);
            return new UserDTO(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public UserDTO login(final LoginDTO loginDTO) {
        User user = userDAO.findByEmail(loginDTO.getEmail());
        if (user == null) {
            throw new NotFoundException(String.format("The user with email %s was not found.", loginDTO.getEmail()));
        }

        final String loginPassword = loginDTO.getPassword();
        if (loginPassword == null || !user.getPassword().equals(securityService.hashPassword(loginPassword))) {
            throw new UnauthorizedException("Login credentials are incorrect");
        }
        user.setLastLogin(new Timestamp(System.currentTimeMillis()));
        return new UserDTO(user);
    }

    @Override
    public UserDTO deleteUser(final String token) {
        final User user = getUserByToken(token);
        user.setActive(false);
        userDAO.save(user);
        return new UserDTO(user);
    }


    private User getUserByToken(final String token) {
        final String email = securityService.validateUserToken(token).getClaim("email").asString();
        if (email == null ) {
            throw new UnauthorizedException("Login credentials are incorrect");
        }
        return userDAO.findByEmail(email);
    }
}
