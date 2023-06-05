package com.bridger.development.services;

import com.bridger.development.model.Participant;
import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.payload.response.destination_entities.ContactListAllParticipants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("participantsService")
public class ParticipantService {

    @Autowired
    UtilityParticipant appVarUtilityParticipant;

    /**
     * <strong>getContactListParticipants<i>()</i></strong><br>
     * Converts the list of 'Participant' instances which are obtained from the database to a list of the tailored
     * instances of the 'ContactListAllParticipants' objects.<br>
     * @param participants A list containing all 'Participant' instances
     * @return A list of 'ContactListAllParticipants' instances.
     */
    public List<ContactListAllParticipants> getContactListParticipants(List<Participant> participants) {
        // Create ArrayList to add the converted results
        List<ContactListAllParticipants> contactList = new ArrayList<>();
        // Loop through the list and passing the appropriate properties to a destination class instantiation
        for (int counter = 0; counter < participants.size(); counter ++) {
            contactList.add(counter, new ContactListAllParticipants());
            ContactListAllParticipants element = contactList.get(counter);
            element.setParticipantNumber(participants.get(counter).getParticipantNumber());
            element.setFullName(appVarUtilityParticipant.composeFullName(participants.get(counter)));
            element.setEmail(participants.get(counter).getEmail());
            element.setTelephone(participants.get(counter).getHomeTelephone());
            element.setCellphone(participants.get(counter).getCellphone());
        }
        // Return the contactList
        return contactList;
    }
}
