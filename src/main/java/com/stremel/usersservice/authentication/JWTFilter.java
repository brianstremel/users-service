package com.stremel.usersservice.authentication;

import com.stremel.usersservice.exception.UnauthorizedException;
import com.stremel.usersservice.service.SecurityService;
import org.springframework.http.HttpStatus;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter implements Filter {

    private final SecurityService securityService;

    public JWTFilter(final SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String token = request.getHeader("token");
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            securityService.validateUserToken(token);
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"mensaje\": \"token not valid, needs authentication\" }");
            response.getWriter().flush();
        }
    }
}
