package com.serviceops.assetdiscovery.config;

import com.serviceops.assetdiscovery.entity.Users;
import com.serviceops.assetdiscovery.exception.ResourceNotFoundException;
import com.serviceops.assetdiscovery.repository.CustomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    private final CustomRepository repository;
    private final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    public ApplicationConfig(CustomRepository repository) {
        this.repository = repository;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repository.findByColumn("email",username,Users.class).orElseThrow(()->{
            logger.error("user not found with email -> {}" , username);
            return new ResourceNotFoundException("User","email",username);
        });
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public  PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}