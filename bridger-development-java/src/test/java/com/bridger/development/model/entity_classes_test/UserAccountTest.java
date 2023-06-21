package com.bridger.development.model.entity_classes_test;

import com.bridger.development.model.UserAccount;
import com.bridger.development.model.UserAccountRole;
import com.bridger.development.model.entity_utility_classes.UtilityUserRole;
import com.bridger.development.model.enums.Roles;
import com.bridger.development.repository.UserAccountRepository;
import com.bridger.development.services.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserAccountTest {

    @Autowired
    private UserAccountService serviceUserAccount;
    @Autowired
    private UserAccountRepository repoUserAccount;
    @Autowired
    private UserAccount userAccount;
    @Autowired
    UtilityUserRole utilityUserRole;

    private static final String userName = "PukK33";

    @Test
    public void repoFindByUserNameTest() {
        /* The requested user account is null when try to get a non-existing user account by username */
        String userName = null;
        UserAccount account = new UserAccount();
        Optional<UserAccount> account_fetched = repoUserAccount.findByUserName(userName);
        assertTrue(account_fetched.isEmpty());

        userName = "";
        account_fetched = repoUserAccount.findByUserName(userName);
        assertTrue(account_fetched.isEmpty());

        account_fetched = repoUserAccount.findByUserName("BlaBla");
        assertTrue(account_fetched.isEmpty());

        /* Happy flow, user account is retrieved with an existing username. */
        userName = "PukP33";
        account_fetched = repoUserAccount.findByUserName(userName);
        if(account_fetched.isPresent()) {
            assertEquals(account_fetched.get().getUserName(), userName);
        }
    }

    @Test
    public void assignUserRolesTest() {

        UserAccount account = new UserAccount();
        Optional<UserAccount> userAccount = Optional.ofNullable(account);

        UserAccountRepository mockedRepoUserAccount = mock(UserAccountRepository.class);
        when(mockedRepoUserAccount.findByUserName(userName)).thenReturn(userAccount);
        account.setUserName(userName);

        /* A new user account instance should have at least USER-rights  */
        UserAccountRole role = utilityUserRole.userRole();
        assertSame(role.getRole().name(), Roles.USER.name());


        /* Get an existing user-account with the stubbed repository method value. */
        userAccount = mockedRepoUserAccount.findByUserName(userName);
        if (!(userAccount.isPresent())) {
            throw new IllegalArgumentException(userName + " not found.");
        }
        assertEquals(userName, account.getUserName());


        /* Find out which roles(s) ar assigned to the user */

        /* Assign a additional role to the user account */
        serviceUserAccount.assignUserRole(account.getUserName(), Roles.MODERATOR );


        /* Assigning a role to an user account which is already assigned is refused */

        /* Remove a role from the user account */
        /* Removing the last role of an user account is prohibited */

    }

}
