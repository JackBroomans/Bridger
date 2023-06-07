package com.bridger.development.model.enum_test;

import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.model.enums.Gender;
import com.bridger.development.model.enums.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EnumTests {

    @Autowired
    private UtilityParticipant appVarParticipant;

    @Test
    public void genderEnumTest() {
        /* Searching and selecting the default gender */
        Gender gender = Gender.getByCode(appVarParticipant.DEFAULT_GENDER);
        assert gender != null;
        assertEquals(appVarParticipant.DEFAULT_GENDER, gender.getCode());

        /* Searching and selecting a gender based on it's code */
        ArrayList<String> invalidCodes =new ArrayList<>();
        invalidCodes.add(null);
        invalidCodes.add("");
        invalidCodes.add("XYZ");
        invalidCodes.add("X");
        invalidCodes.forEach((String test) -> {
            assertNull(Gender.getByCode(null));
        });
        assertEquals(Gender.getByCode("M"), Gender.MALE);
    }

    @Test
    public void userRoleEnumTest() {
        /* Searching and selecting the default gender */
        Roles role = Roles.getDefaultSetting();
        assertEquals(Roles.USER, role);
        role = Roles.MODERATOR;
        assertNotEquals(role, Roles.getDefaultSetting());
    }
}
