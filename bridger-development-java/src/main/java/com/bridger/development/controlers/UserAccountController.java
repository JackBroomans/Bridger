package com.bridger.development.controlers;

import com.bridger.development.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bridger")
@CrossOrigin(origins = "http://localhost:4200")
@EnableWebSecurity
public class UserAccountController {

    @Autowired
    UserAccountService service;

    @GetMapping(path = "/login")
    public String userAuthentication() {
        return service.getAuthenticationMessage("You are authenticated");
    }

}
