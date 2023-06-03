package com.bridger.development.entity_classes_test;

import com.bridger.development.model.UserAccount;
import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.model.entity_utility_classes.UtilityUserAccount;
import com.bridger.development.util.StringFunctions;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaticipantTest {

    @Autowired
    UtilityParticipant appVarParticipant;

    @Autowired
    UtilityUserAccount appVarUserAccount;


    @Test
    public void participantNumberTest() {
        /* The construction and format must meet the requirements as described in 'MODEL.md'. */
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyMMdd-mmSSS");
        StringBuilder builder = new StringBuilder()
                .append(LocalDateTime.now().format(dateFormat))
                .append("-");
        assertFalse(builder.toString().matches(appVarParticipant.REGEX_PARTICIPANT_NUMBER));

        builder.append(StringFunctions.addLeadingZeros(String.valueOf(new Random().nextInt(1000)), 3));
        assertTrue(builder.toString().matches(appVarParticipant.REGEX_PARTICIPANT_NUMBER));
    }

    @Test
    public void newParticipantTest() {
        /* First we need a user account to link the participant to, and provide it with the required properties. */
        UserAccount userAccount = appVarUserAccount.userAccount();
        userAccount.setUserName(appVarUserAccount.PREDEFINED_USER_NAME);
        userAccount.setPassword(appVarUserAccount.PREDEFINED_USER_PASSWORD);

    }


}
