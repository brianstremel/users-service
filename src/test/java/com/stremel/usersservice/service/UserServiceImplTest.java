package com.stremel.usersservice.service;

import com.stremel.usersservice.dao.UserDAO;
import com.stremel.usersservice.dto.PhoneDTO;
import com.stremel.usersservice.dto.UserCreationDTO;
import com.stremel.usersservice.dto.UserDTO;
import com.stremel.usersservice.exception.BadRequestException;
import com.stremel.usersservice.exception.UserAlreadyExistsException;
import com.stremel.usersservice.model.User;
import com.stremel.usersservice.utils.RegexUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDAO userDAO;

    @Mock
    private SecurityService securityService;

    @Mock
    private RegexUtils regexUtils;

    @Nested
    public class CreateUserTest {

        @Test
        public void whenAUnexistingUserIsBeingCreatedWithAValidPasswordShouldBeCreated() {
            // GIVEN
            final var userCreationDTO = new UserCreationDTO(
                    "brian@email.com",
                    "12345678",
                    List.of(new PhoneDTO("42001800", "11", "54"))
            );

            doNothing().when(regexUtils).validatePassword(anyString());
            when(securityService.generateUUID()).thenReturn("uuid");
            when(securityService.hashPassword(anyString())).thenReturn("hashed-password");
            when(securityService.generateUserToken(anyString(), anyString())).thenReturn("user-token");
            when(userDAO.save(any(User.class))).thenReturn(new User());
            // WHEN
            final UserDTO response = userService.createUser(userCreationDTO);

            // THEN
            Assertions.assertEquals("uuid", response.getId());
            Assertions.assertEquals("user-token", response.getToken());
            Assertions.assertTrue(response.isActive());
        }

        @Test
        public void whenThePasswordIsNotValidShouldThrowAnException() {
            // GIVEN
            final var userCreationDTO = new UserCreationDTO(
                    "brian@email.com",
                    "12345678",
                    List.of(new PhoneDTO("42001800", "11", "54"))
            );

            doNothing().when(regexUtils).validatePassword(anyString());
            when(securityService.generateUUID()).thenReturn("uuid");
            when(securityService.hashPassword(anyString())).thenReturn("hashed-password");
            when(securityService.generateUserToken(anyString(), anyString())).thenReturn("user-token");
            when(userDAO.save(any(User.class))).thenThrow(new DataIntegrityViolationException(""));
            // WHEN
            final Executable executable = () -> userService.createUser(userCreationDTO);

            // THEN
            Assertions.assertThrows(UserAlreadyExistsException.class, executable);
        }

        @Test
        public void whenTryingToCreateAUserWhenAnExistingEmailShouldThrowAnException() {
            // GIVEN
            final var userCreationDTO = new UserCreationDTO(
                    "brian@email.com",
                    "12345678",
                    List.of(new PhoneDTO("42001800", "11", "54"))
            );

            doThrow(BadRequestException.class).when(regexUtils).validatePassword(anyString());

            // WHEN
            final Executable executable = () -> userService.createUser(userCreationDTO);

            // THEN
            Assertions.assertThrows(BadRequestException.class, executable);
        }
    }

    @Nested
    public class UpdateUser {
        @Test
        public void whenAUserIsBeingUpdatedShouldReturnAObjectWithDeUpdatedInfo() {
        }

        @Test
        public void whenThePasswordIsNotValidShouldThrowAnException() {
        }

        @Test
        public void whenTryingToCreateAUserWhenAnExistingEmailShouldThrowAnException() {
        }
    }

    @Nested
    public class Login {
        @Test
        public void whenTryingToLoginWithCorrectInfoShouldReturnUserDataWithUpdatedLastLoginDate() {
        }

        @Test
        public void whenTheUserDoesNotExistShouldThrowAnException() {
        }

        @Test
        public void whenPasswordIsIncorrectShouldThrowAnException() {
        }
    }

    @Nested
    public class DeleteUser {
        @Test
        public void whenTryingToDeleteWithCorrectTokenShouldSetActiveToFalseAndReturnUserInfo() {
        }

        @Test
        public void whenTokenContainsIncorrectInformationShouldThrowAnException(){
        }
    }
}