package com.bridger.development.services;

import com.bridger.development.model.UserAccount;
import com.bridger.development.model.UserAccountRole;
import com.bridger.development.model.entity_utility_classes.UtilityUserRole;
import com.bridger.development.model.enums.Roles;
import com.bridger.development.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.bridger.development.util.StringFunctions.validateString;

@Service("UtilityUserAccountService")
public class UserAccountService {

    @Autowired
    private UtilityUserRole appVarUtilityUserRole;

    @Autowired
    private UserAccountRepository repoUserAccount;

    private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);


    public UserAccount addNewUserAccount() {
        UserAccount account = new UserAccount();
//        UserAccount account = appVarUtilityUserAccount.userAccount();
        repoUserAccount.save(account);
        UserAccountRole userAccountRole = appVarUtilityUserRole.userRole();

        // Todo: Add repository for useraccount-role and save linked entities before testng.
        // Todo bring function to utility class of UserAccount.

        return account;
    }

    /**
     * <strong>assignUserRole<i>(UserRole)</i></strong><br>
     * Assigns a role to a user account, if not already assigned. Replaces the regular setter of Roles.
     * @param userName The user name of the account to assign the role to.
     * @param role The role to assign to the user account.
     * @return A set containing the roles assigned to the user account. An empty set will be returned  when the user account is not found or the role could be added.
     * @throws IllegalArgumentException When one of the arguments is invalid or the assignment would add an identical,
     * already assigned, role.
     */
    public Set<UserAccountRole> assignUserRole(String userName, Roles role) {
        Set<UserAccountRole> roles = new HashSet<>();

        if(!validateString(userName) && role != null) {
            Optional<UserAccount> account_fetched = repoUserAccount.findByUserName(userName);
            if (account_fetched.isPresent()) {

                if (account_fetched.get().getRoles().contains(role))
                    logger.warn("Role already assigned to user: {}.", role);
                else {
                    roles.addAll(account_fetched.get().getRoles());
                    logger.info("Role {} assigned to user {}", role, userName);
                }
            }
            logger.error("User(name) {} not found!", userName);
        }
        return roles;
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
