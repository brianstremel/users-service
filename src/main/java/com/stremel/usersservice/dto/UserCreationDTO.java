package com.stremel.usersservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;


public class UserCreationDTO {

    public UserCreationDTO() {}

    public UserCreationDTO(final String email, final String password, final List<PhoneDTO> phones) {
        this.email = email;
        this.password = password;
        this.phones = phones;
    }
    @Email(regexp = ".+[@].+[\\.].+")
    @NotBlank(message = "Field 'email' is required")
    private String email;

    @NotBlank(message = "Field 'password' is required")
    private String password;
    private List<PhoneDTO> phones;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(final List<PhoneDTO> phoness) {
        this.phones = phoness;
    }
}
