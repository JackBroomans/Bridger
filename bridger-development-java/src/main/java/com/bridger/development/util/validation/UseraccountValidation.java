package com.bridger.development.util.validation;

import com.bridger.development.model.entity_utility_classes.UtilityUserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.PatternSyntaxException;

@Component
public class UseraccountValidation {

    private static final Logger logger = LoggerFactory.getLogger(ParticipantValidation.class);

    private static UtilityUserAccount appVar;

    @Autowired
    public UseraccountValidation(UtilityUserAccount appVar) {
        UseraccountValidation.appVar = appVar;
    }

    /**
     * <strong>validateUserName<i>(String)</i></strong><br>
     * Checks against a regular expression if a given username meets te requirements of a username.
     * @param username The string which is checked.
     * @return A boolean indicating if the given name meets the requirements.
     * @throws PatternSyntaxException When the regular expression isn't valid.
     */
    public static boolean validateUserName(String username) throws PatternSyntaxException {
        if (username.matches(appVar.REGEX_USER_NAME)) {
            return true;
        }
        logger.warn(appVar.MSG_INVALID_USERNAME_FORMAT);
        return false;
    }
}
