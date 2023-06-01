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
        this.appVar = appVar;
    }


    public static boolean validateUserName(String username) throws PatternSyntaxException {
        if (username.matches(appVar.REGEX_USER_NAME)) {
            return true;
        }
        logger.warn(appVar.MSG_INVALID_USERNAME_FORMAT);
        return false;
    }
}
