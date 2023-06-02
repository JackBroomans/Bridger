package com.bridger.development;

import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.util.StringFunctions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BridgerUtilitiesTest {

    @Autowired
    UtilityParticipant appVarParticipant;

    @Test
    public void addleadingZerosTest() {
        /*  A string parameter which length exceed the requested length, shouldn't trow an error. */
        String veryLongString = "Very_Long_String";
        assertDoesNotThrow(()->StringFunctions.addLeadingZeros(veryLongString, 8));
        assertEquals(veryLongString, StringFunctions.addLeadingZeros(veryLongString, 8));

        /* Passing the value null or a string which contains whitespaces throws an IllegalArgumentException */
        assertThrows(IllegalArgumentException.class, ()-> StringFunctions.addLeadingZeros(null, 8));

        final String stringWithSpaces = "very long string";
        assertThrows(IllegalArgumentException.class, ()-> StringFunctions.addLeadingZeros(stringWithSpaces,8));

        /* An empty string parameter will contain the number of zero's of the length parameter */
        assertEquals("000", StringFunctions.addLeadingZeros("", 3));

        /* Happy flow */
        assertEquals("007", StringFunctions.addLeadingZeros("7", 3));
        assertTrue(appVarParticipant.generateParticipantNumber().matches(appVarParticipant.REGEX_PARTICIPANT_NUMBER));
    }
}
