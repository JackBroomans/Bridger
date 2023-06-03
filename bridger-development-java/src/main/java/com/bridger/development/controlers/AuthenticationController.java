package com.bridger.development.controlers;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bridger")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@EnableWebSecurity
public class AuthenticationController {
}
