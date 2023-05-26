package com.befrank.casedeveloperjava.controlers;

import com.befrank.casedeveloperjava.model.Participant;
import com.befrank.casedeveloperjava.repository.ParticipantRepository;
import com.befrank.casedeveloperjava.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/bridger")
@CrossOrigin(origins = "http://localhost:4200")
public class ParticipantController {

    @Autowired
    private final ParticipantRepository repository;

    @Autowired
    ParticipantService service;

    public ParticipantController(ParticipantRepository repository) {
        this.repository = repository;
    }

    /**
     * <strong>getAllParticipants</strong><i>()</i><br>
     * Gets all participants from the 'Participant' table.
     * @return A list containing all the participants.
     */
    @RequestMapping("/participants")
    public @ResponseBody
    List<Participant> getAllParticipants() {
        return service.getAllParticipants();
    }

    /**
     * <strong>haalDeelnemerOpId(<i>long</i>)</strong><br>
     * Haalt een deelnemer op uit de database op basis van de Id van die deelnemer.<br>
     * @param id Het kenmerk (id) van de deelnemer waarnaar wordt gezocht.
     * @return een instantie van de gezochte Deelnemer
     * @throws NoSuchElementException indien de deelnemer niet is gevonden.
     */
    @GetMapping("/participant/{id}")
    Participant haalDeelnemerOpId(@PathVariable long id) {
        return this.repository.findById(id);
    }

    /**
     * <strong>haalDeelnemerOpEmailAdres(<i>String</i>)</strong><br>
     * Zoek een deelnemer op basis van het email-adress.<br>
     * @param email Het email-adres van de deelnemer die wordt gezocht.
     * @return De deelnemer met het betreffende email-adres
     */
    @GetMapping("/participant/{email}")
    Participant haalDeelnemerOpEmailAdres(@PathVariable String email) {
        return this.repository.findByEmail(email);
    }

    /**
     * <strong>haalDeelnemerOpDeelnemersnummer(<i>String</i>)</strong><br>
     * Zoek een deelnemer op basis van diens deelnemersnummer.<br>
     * @param deelnemersnummer Het deelnemersnummer van de deelnemer waarnaar wordt gezocht.
     * @return De deelnemer met het betreffende email-adres
     */
    @GetMapping("/participant/{deelnemersnummer}")
    Participant haalDeelnemerOpDeelnemersnummer(@PathVariable String deelnemersnummer) {
        return this.repository.findByParticipantNumber(deelnemersnummer);
    }

    /**
     * <strong>berekenJaarlijksePremieDeelnemer(String)</strong><br>
     * Berekend de jaarlijks (te beleggen) premie uit de (vastgelegde) premiegegevens van een bepaalde deelnemer.<br>
     * (Full-time salaris – Franchise) * Parttime percentage * Beschikbare premie percentage<br>
     * @param paricipationNumber
     * @return
     */
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
