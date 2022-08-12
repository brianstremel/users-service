package com.stremel.usersservice.utils;

import com.stremel.usersservice.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexUtils {

    private final String passwordRegex;
    private final String passwordError;

    public RegexUtils(@Value("${password.regex}") final String passwordRegex,
                      @Value("${password.error}") final String passwordError) {
        this.passwordRegex = passwordRegex;
        this.passwordError = passwordError;
    }

    public void validatePassword(final String password) {
        System.out.printf("regex: %s /n error: %s%n", passwordRegex, passwordError);
        Pattern p = Pattern.compile(passwordRegex);
        Matcher m = p.matcher(password);
        if(!m.matches()) {
            throw new BadRequestException(passwordError);
        }

    }
}
