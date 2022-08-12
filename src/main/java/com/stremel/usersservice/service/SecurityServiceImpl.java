package com.stremel.usersservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.stremel.usersservice.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final String secret;
    private final byte[] salt;

    public SecurityServiceImpl(@Value("${jwt.secret}") final String secret,
                               @Value("${jwt.secret}") final String salt) {
        this.secret = secret;
        this.salt = salt.getBytes(); // Not generated and saved for practical purposes
    }

    public String hashPassword(final String id) {
        try {
            final KeySpec spec = new PBEKeySpec(id.toCharArray(), salt, 65536, 128);
            final SecretKeyFactory factory;
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Arrays.toString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("The password could not be hashed");
        }
    }

    @Override
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }


    @Override
    public String generateUserToken(final String uuid, final String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withPayload(Map.of("email", email))
                    .withKeyId(uuid)
                    .withIssuer("auth0")
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating user token");
        }
    }

    @Override
    public DecodedJWT validateUserToken(final String token) {
        try {
            if (token == null) {
                throw new UnauthorizedException("Invalid access token");
            }
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception){
            throw new UnauthorizedException("Invalid access token");
        }
    }
}
