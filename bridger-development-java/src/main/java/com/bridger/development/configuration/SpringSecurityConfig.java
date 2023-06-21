package com.bridger.development.configuration;

import com.bridger.development.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


/**
 * <strong>SpringSecurityConfig</strong><br>
 * Configuration-class Where the SpringBoot security settings are kept
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  {

    @Autowired
    private UserAccount userAccount;

    /**
     * <strong>buildSecurityFilterChain(<i>HttpSecurity</i>)</strong><br>
     * The method build, based on a default Spring Boot HttpSecurity object, the Security Filter Chain, which specifies:
     * <ol>
     *     <li>Which endpoint ar reachable with and without authentication.</li>
     *     <li>Which sign-in and sign-out forms are used, built-in or custom.</li>
     * </ol>
     * @param http The Spring Boot HttpSecurity object which holds the built security chain and settings.
     * @return A Spring Boot SecurityFilterChain object containing teh required security properties
     * @throws Exception Whenever the builder is unable to build a valid security filter chain.
     */
    @Bean
    public SecurityFilterChain buildSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/support").hasRole("SUPPORT")
                        .requestMatchers("/participant").hasAnyRole("USER", "MODERATOR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/bridger/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    /**
     * <strong>userDetailsService<i>()</i></strong><br>
     * Specifies the properties of a special <i>'in memory'</i>for testing. These properties are handled by
     * the Spring Security <i>InMemoryUserDetailsManager</i> and are taken from the currently singed-in participant.
     * @return A Spring Boot security configuration object (InMemoryUserDetailsManager) which keeps the credentials
     * of the current signed in participant (or moderator, admin or support employee) in memory.
     * @Note In this case the username is taken from the external configuration setting.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername(userAccount.PREDEFINED_USER_NAME)
                .password(userAccount.PREDEFINED_USER_PASSWORD)
                .roles(userAccount.PREDEFINED_USER_ROLES)
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
