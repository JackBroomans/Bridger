package com.bridger.development.controlers;

import com.bridger.development.model.Participant;
import com.bridger.development.payload.response.destination_entities.ContactListAllParticipants;
import com.bridger.development.repository.ParticipantRepository;
import com.bridger.development.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/bridger")
@CrossOrigin(origins = "http://localhost:4200")
@EnableWebSecurity
public class ParticipantController {

    @Autowired
    private final ParticipantRepository repository;

    public ParticipantController(ParticipantRepository repository) {
        this.repository = repository;
    }

    @Autowired
    ParticipantService service;

    /**
     * <strong>getAllParticipants<i>()</i></strong><br>
     * @return A list in JSON-format containing all 'Participants' instances.
     */
    @RequestMapping("/participant")
    public @ResponseBody
    List<Participant> getAllParticipants() {
        return repository.findAll();
    }

    /**
     * <strong>getContactListAllParticipants<i>()</i></strong><br>
     * @return A list in JSON-format containing the contact items (telephone, cel phone and email address) of all
     * participants.
     */
    @RequestMapping("participant/contactlist")
    public @ResponseBody
    List<ContactListAllParticipants> getContactListAllParticipants() {
        return service.getContactListParticipants(repository.findAll());
    }

    /**
     * <strong>getParticipantOnId(<i>long</i>)</strong><br>
     * Fetches an instance of a 'Participant' object from the database based on the identifier.<br>
     * @param id The identifier of the participant.
     * @return The instance of the requested 'Participant' object
     * @throws NoSuchElementException when the participant isn't found.
     */
    @GetMapping("/participant/id/{id}")
    Participant getParticipantOnId(@PathVariable long id) {
        return this.repository.findById(id);
    }

    /**
     * <strong>getParticipantOnEmail<i>(String)</i></strong><br>
     * Searches and fetches a participant base on the email-address<br>
     * @param email The email-address of the participant.
     * @return The participant
     */
    @GetMapping("/participant/email/{email}")
    Participant getParticipantOnEmail(@PathVariable String email) {
        return this.repository.findByEmail(email);
    }

    /**
     * <strong>getParticipantByNumber(<i>String</i>)</strong><br>
     * Searches and returns a participant who had a particular participant number.<br>
     * @param participantNumber The participant number of the requested participant.
     * @return The participant who owns the participant number.
     */
    @GetMapping("/participant/{participantNumber}")
    Participant getParticipantByNumber(@PathVariable String participantNumber) {
        return this.repository.findByParticipantNumber(participantNumber);
    }

//    /**
//     * <strong>berekenJaarlijksePremieDeelnemer(String)</strong><br>
//     * Berekend de jaarlijks (te beleggen) premie uit de (vastgelegde) premiegegevens van een bepaalde deelnemer.<br>
//     * (Full-time salaris – Franchise) * Parttime percentage * Beschikbare premie percentage<br>
//     * @param paricipationNumber
//     * @return
//     */
//    @GetMapping("/premium/{paricipationNumber}")
//    public double berekenJaarlijksePremieDeelnemer(String paricipationNumber) {
//
//        // Haal deelnemer en de bijbehorende premiegegevens op.
//        Participant participant = repository.findByParticipantNumber(paricipationNumber);
//
//        // Bepaal de som de huidide beleggingen (-> stored procedure)
//        double waardeHuidigeBeleggingen = repository.getSumInvestmentsParticipant(participant.getId());
//
//        // Bereken de premie en retourneer deze
//        return (participant.getParticipantPremium().getFullTimeSalaris() - participant.getParticipantPremium().getFranciseActueel())
//                * (participant.getParticipantPremium().getParttimePercentage() / 100)
//                * (participant.getParticipantPremium().getPercentageBeschikbarePremie()/100);
//    }





}
