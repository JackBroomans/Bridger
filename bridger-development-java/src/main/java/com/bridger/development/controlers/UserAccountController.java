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


//    @PostMapping(path = "/auth/signup")

    @PostMapping(path = "/auth/signin")
    @ResponseBody
    public String userAuthentication(@RequestParam String userName, @RequestParam String password) {

        return service.getAuthenticationMessage("You are authenticated");
    }

//    @PostMapping(path = "/auth/signout/all")

//    @PostMapping(path = "/auth/data/all")

//    @GetMapping(path = "/auth/data/admin")

//    @GetMapping(path = "/auth/data/support")

//    @GetMapping(path = "auth/data/moderator")

//    @GetMapping(path = "auth/data/user")

//    }

}
