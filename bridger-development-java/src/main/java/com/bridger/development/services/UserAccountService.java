package com.bridger.development.services;

import org.springframework.stereotype.Service;

@Service("UserAccountService")
public class UserAccountService {

    public String getAuthenticationMessage(String message) {
        // Get user account based on the login name.
        // Perform validation checks on the login account
        // Check the password
        // Set login attempt counter to 0
        // Set last login date to current date

        return String.format("UserAccountService\n%s]", message);

    }
}
