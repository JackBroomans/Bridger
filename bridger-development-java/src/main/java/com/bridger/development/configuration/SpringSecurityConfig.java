package com.bridger.development.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <strong>SpringSecurityConfig</strong><br>
 * Configuration class for the Spring security settings for the 'Spring WebSecurity' practice.
 * <a href="https://github.com/eugenp/tutorials/blob/master/spring-boot-modules/spring-boot-security/src/main/java/com/baeldung/annotations/websecurity">...</a>
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("briger/admin").hasRole("ADMIN")
                        .requestMatchers("bridger/participant").hasAnyRole("USER", "MODERATOR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

}
