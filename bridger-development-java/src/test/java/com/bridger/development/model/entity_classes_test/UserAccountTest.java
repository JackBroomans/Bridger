package com.bridger.development.model.entity_classes_test;

import com.bridger.development.model.UserAccount;
import com.bridger.development.model.UserRole;
import com.bridger.development.model.entity_utility_classes.UtilityUserAccount;
import com.bridger.development.model.entity_utility_classes.UtilityUserRole;
import com.bridger.development.model.enums.Roles;
import com.bridger.development.repository.UseraccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserAccountTest {

    @Autowired
    private UseraccountRepository repoUserAccount;

    @Autowired
    private UtilityUserAccount utilityUserAccount;

    @Autowired
    UtilityUserRole utilityUserRole;

    @Test
    public void assignUserRolesTest() {
        /* A new user account instance should have at least USER-rights  */
        UserAccount account = utilityUserAccount.userAccount();
        UserRole role = utilityUserRole.userRole();
        assertTrue(role.getRole().name() == Roles.USER.name());


        /* Find out which roles(s) ar assigned to the user */

        /* Assign a additional role to the user account */
        /* Assigning a role to an user account which is already assigned is refused */

        /* Remove a role from the user account */
        /* Removing the last role of an user account is prohibited */

    }

}
