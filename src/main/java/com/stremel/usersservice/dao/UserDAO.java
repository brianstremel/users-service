package com.stremel.usersservice.dao;

import com.stremel.usersservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserDAO extends CrudRepository<User, UUID> {

    User findByEmail(String email);
}
