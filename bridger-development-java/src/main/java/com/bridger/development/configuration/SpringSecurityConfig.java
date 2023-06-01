package com.bridger.development.configuration;

import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
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
 * Configuration class for the Spring security settings for the 'Spring WebSecurity' practice.
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

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername(appVar.PREDEFINED_USER_NAME)
                .password(appVar.PREDEFINED_USER_PASSWORD)
                .roles(appVar.PREDEFINED_USER_ROLES)
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
