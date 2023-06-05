package com.bridger.development.configuration;

import com.bridger.development.model.entity_utility_classes.UtilityUserAccount;
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
public class SpringSecurityConfig {

    @Autowired
    UtilityUserAccount appVar;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/participant").hasAnyRole("USER", "MODERATOR", "SUPPORT")
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
     * Specifies the properties of a special <i>'in memory'</i>for system operations. These properties are handled by
     * the Spring Security <i>InMemoryUserDetailsManager</i> and are fetched from the external configuration settings.
     * @return The user who is used as default (system) user in 'In Memory' operations.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername(appVar.PREDEFINED_USER_NAME)
                .password(appVar.PREDEFINED_USER_PASSWORD)
                .roles(appVar.PREDEFINED_USER_ROLES)
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
