package com.stremel.usersservice.service;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface SecurityService {
    String hashPassword(final String password);
    String generateUUID();
    String generateUserToken(final String uuid, final String email);
    DecodedJWT validateUserToken(final String token);
}
