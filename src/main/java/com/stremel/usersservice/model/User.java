package com.stremel.usersservice.model;

import com.stremel.usersservice.dto.UserCreationDTO;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "`user`")
public class User {

    @Id
    private String id;


    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phones;

    private Timestamp created;
    private Timestamp modified;
    private Timestamp lastLogin;
    private boolean isActive;
    private String token;

    public User() {}

    public User(final String userId,
                final UserCreationDTO userCreationDTO,
                final String hashedPassword,
                final String token) {
        this.id = userId;
        this.email = userCreationDTO.getEmail();
        this.password = hashedPassword;
        this.phones = userCreationDTO.getPhones().stream().map(Phone::new).collect(Collectors.toList());
        final Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        this.created = currentTimestamp;
        this.modified = currentTimestamp;
        this.lastLogin = currentTimestamp;
        this.isActive = true;
        this.token = token;
    }


    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

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

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(final List<Phone> phones) {
        this.phones = phones;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(final Timestamp created) {
        this.created = created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(final Timestamp modified) {
        this.modified = modified;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(final Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(final boolean active) {
        isActive = active;
    }


}
