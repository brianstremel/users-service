package com.stremel.usersservice.configuration;

import com.stremel.usersservice.authentication.JWTFilter;
import com.stremel.usersservice.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilter(@Autowired SecurityService securityService) {
        final FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JWTFilter(securityService));
        registrationBean.addUrlPatterns("/v1/users");
        return registrationBean;
    }
}
