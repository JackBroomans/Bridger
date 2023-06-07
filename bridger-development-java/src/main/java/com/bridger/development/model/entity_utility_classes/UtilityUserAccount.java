package com.bridger.development.model.entity_utility_classes;

import com.bridger.development.model.UserAccount;
import com.bridger.development.model.enums.Roles;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;
import java.util.List;

@Configuration
@PropertySource("classpath:appVar.yml")
@ConfigurationProperties(prefix = "user-account")
public class UtilityUserAccount {

    @Value("${regex_user_name}")
    public String REGEX_USER_NAME;

    @Value("${login_attempts}")
    public String LOGIN_ATTEMPTS;

    @Value("${days_password_valid}")
    public String DAYS_PASSWORD_VALID;

    @Value("${maximum_days_not_used}")
    public String MAX_DAYS_NOT_USED;

    @Value("${user.name}")
    public String PREDEFINED_USER_NAME;

    @Value("${user.password}")
    public String PREDEFINED_USER_PASSWORD;

    @Value("${user.roles}")
    public String PREDEFINED_USER_ROLES;

    @Value("${invalid_username_format}")
    public String MSG_INVALID_USERNAME_FORMAT;
    
    @PostConstruct
    public void init() {
    }

    /**
     * <strong>useraccount<i>()</i></strong><br>
     * To assign the default settings on instantiation of an entity class without using field assignments,
     * instantiation of an entity should be done by this method.
     * @return A new instance of a UserAccount, with the default settings.
     */
    public UserAccount userAccount() {
        UserAccount account = new UserAccount();
        account.setLoginAttempts(0);
        account.setDateRegistered(LocalDate.now());
        account.setDateLastChanged(LocalDate.now());
        account.setActive(false);
        account.setLocked(false);
        return account;
    }
}
