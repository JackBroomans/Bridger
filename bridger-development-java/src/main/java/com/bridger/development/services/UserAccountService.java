package com.bridger.development.services;

import com.bridger.development.model.UserAccount;
import com.bridger.development.model.UserRole;
import com.bridger.development.model.entity_utility_classes.UtilityUserAccount;
import com.bridger.development.model.entity_utility_classes.UtilityUserRole;
import com.bridger.development.repository.UseraccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserAccountService")
public class UserAccountService {

    @Autowired
    private UtilityUserAccount appVarUtilityUserAccount;

    @Autowired
    private UtilityUserRole appVarUtilityUserRole;

    @Autowired
    private UseraccountRepository appVarUseraccountRepository;


    public UserAccount addNewUserAccount() {
        UserAccount account = appVarUtilityUserAccount.userAccount();
        appVarUseraccountRepository.save(account);
        UserRole userRole = appVarUtilityUserRole.userRole();

        // Todo: Add repository for useraccountrole and save linked entities before testng.
        // Todo bring function to utility class of UserAccount.

        return account;

    }

    // Todo: remove when other services are implemented.
    public String getAuthenticationMessage(String message) {
        // Get user account based on the login name.
        // Perform validation checks on the login account
        // Check the password
        // Set login attempt counter to 0
        // Set last login date to current date

        return String.format("UserAccountService\n%s]", message);

    }
}
