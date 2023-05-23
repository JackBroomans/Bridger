package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Participant;

import com.befrank.casedeveloperjava.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/case")
@CrossOrigin(origins = "http://localhost:4200")
public class DeelnemerController {

    @Autowired
    private final ParticipantRepository repository;

    DeelnemerController(ParticipantRepository repository) {
        this.repository = repository;
    }

    /**
     * <strong>getDeelnemers()</strong><br>
     * Haalt alle deelnemers uit de database op en plaatst deze in een ArrayList.<br>
     * @return Een List waarin alle gevonden deelnemers zijn opgenomen.
     */
    @GetMapping("/deelnemers")
    List<Participant> haalAlleDeelnemers() {
        return new ArrayList<>(this.repository.findAll());
    }

    /**
     * <strong>haalDeelnemerOpId(<i>long</i>)</strong><br>
     * Haalt een deelnemer op uit de database op basis van de Id van die deelnemer.<br>
     * @param id Het kenmerk (id) van de deelnemer waarnaar wordt gezocht.
     * @return een instantie van de gezochte Deelnemer
     * @throws NoSuchElementException indien de deelnemer niet is gevonden.
     */
    @GetMapping("/deelnemer/{id}")
    Participant haalDeelnemerOpId(@PathVariable long id) {
        return this.repository.findById(id);
    }

    /**
     * <strong>haalDeelnemerOpEmailAdres(<i>String</i>)</strong><br>
     * Zoek een deelnemer op basis van het email-adress.<br>
     * @param email Het email-adres van de deelnemer die wordt gezocht.
     * @return De deelnemer met het betreffende email-adres
     */
    @GetMapping("/deelnemr/{email}")
    Participant haalDeelnemerOpEmailAdres(@PathVariable String email) {
        return this.repository.findByEmail(email);
    }

    /**
     * <strong>haalDeelnemerOpDeelnemersnummer(<i>String</i>)</strong><br>
     * Zoek een deelnemer op basis van diens deelnemersnummer.<br>
     * @param deelnemersnummer Het deelnemersnummer van de deelnemer waarnaar wordt gezocht.
     * @return De deelnemer met het betreffende email-adres
     */
    @GetMapping("/deelnemer/{deelnemersnummer}")
    Participant haalDeelnemerOpDeelnemersnummer(@PathVariable String deelnemersnummer) {
        return this.repository.findByParticipantNumber(deelnemersnummer);
    }

    /**
     * <strong>berekenJaarlijksePremieDeelnemer(String)</strong><br>
     * Berekend de jaarlijks (te beleggen) premie uit de (vastgelegde) premiegegevens van een bepaalde deelnemer.<br>
     * (Full-time salaris – Franchise) * Parttime percentage * Beschikbare premie percentage<br>
     * @param deelnemersnummer
     * @return
     */
    @GetMapping("/premie/{deelnemersnummer}")
    public double berekenJaarlijksePremieDeelnemer(String deelnemersnummer) {

        // Haal deelnemer en de bijbehorende premiegegevens op.
        Participant participant = repository.findByParticipantNumber(deelnemersnummer);

        // Bepaal de som de huidide beleggingen (-> stored procedure)
        double waardeHuidigeBeleggingen = repository.getSumInvestmentsParticipant(participant.getId());

        // Bereken de premie en retourneer deze
        return (participant.getParticipantPremium().getFullTimeSalaris() - participant.getParticipantPremium().getFranciseActueel())
                * (participant.getParticipantPremium().getParttimePercentage() / 100)
                * (participant.getParticipantPremium().getPercentageBeschikbarePremie()/100);
    }





}
