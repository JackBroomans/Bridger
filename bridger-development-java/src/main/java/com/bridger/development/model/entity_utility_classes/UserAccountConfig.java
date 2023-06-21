package com.bridger.development.model.entity_utility_classes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@PropertySource("classpath:appVar.yml")
@ConfigurationProperties(prefix = "user-account")
public abstract class UserAccountConfig {

    /* Externalized configuration settings and messages */
    @Value("${regex_user_name}")
    public String REGEX_USER_NAME;

    @Value("${login_attempts}")
    public String MAX_LOGIN_ATTEMPTS;

    @Value("${days_password_valid}")
    public String DAYS_PASSWORD_VALID;

    @Value("${maximum_days_not_used}")
    public String MAX_DAYS_NOT_USED;

    @Value("${username}")
    public String PREDEFINED_USER_NAME;

    @Value("${password}")
    public String PREDEFINED_USER_PASSWORD;

    @Value("${user.roles}")
    public String PREDEFINED_USER_ROLES;

    @Value("${invalid_username_format}")
    public String MSG_INVALID_USERNAME_FORMAT;
}
