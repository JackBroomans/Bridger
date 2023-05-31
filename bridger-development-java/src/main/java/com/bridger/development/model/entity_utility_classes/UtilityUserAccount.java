package com.bridger.development.model.entity_utility_classes;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:appVar.yml")
@ConfigurationProperties(prefix = "security")
public class UtilityUserAccount {

    @Value("${user.name}")
    public String PREDEFINED_USER_NAME;

    @Value("${user.password}")
    public String PREDEFINED_USER_PASSWORD;

    @Value("${user.roles}")
    public String PREDEFINED_USER_ROLES;

    @PostConstruct
    public void init() {
    }

}
