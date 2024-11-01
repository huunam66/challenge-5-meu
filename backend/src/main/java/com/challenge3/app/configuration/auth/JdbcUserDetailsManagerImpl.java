package com.challenge3.app.configuration.auth;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcUserDetailsManagerImpl extends JdbcUserDetailsManager {
    public JdbcUserDetailsManagerImpl(DataSource dataSource) {
        super(dataSource);

        this.setUsersByUsernameQuery("SELECT u.email as username, u.password, u.enabled FROM users u WHERE u.email = ?");

        this.setAuthoritiesByUsernameQuery("SELECT a.email as username, CONCAT('ROLE', '_', a.name) as authority FROM authorities a WHERE a.email = ?");
    }
}
