package com.stremel.usersservice.controller;

import com.stremel.usersservice.dao.UserDAO;
import com.stremel.usersservice.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/audit")
public class AuditController {

    private final UserDAO userDAO;


    public AuditController(final UserDAO userDAO) {
        this.userDAO = userDAO;

    }

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> result = new ArrayList<>();
        userDAO.findAll().forEach(result::add);
        return result;
    }
}
