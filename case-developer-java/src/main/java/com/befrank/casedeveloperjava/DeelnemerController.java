package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;

import com.befrank.casedeveloperjava.repository.DeelnemerRepository;
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
    private final DeelnemerRepository repository;

    DeelnemerController(DeelnemerRepository repository) {
        this.repository = repository;
    }

    /**
     * <strong>getDeelnemers()</strong><br>
     * Haalt alle deelnemers uit de database op en plaatst deze in een ArrayList.<br>
     * @return Een List waarin alle gevonden deelnemers zijn opgenomen.
     */
    @GetMapping("/deelnemers")
    List<Deelnemer> haalAlleDeelnemers() {
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
    Deelnemer haalDeelnemerOpId(@PathVariable long id) {
        return this.repository.findById(id);
    }

    /**
     * <strong>haalDeelnemerOpEmailAdres(<i>String</i>)</strong><br>
     * Zoek een deelnemer op basis van het email-adress.<br>
     * @param email Het email-adres van de deelnemer die wordt gezocht.
     * @return De deelnemer met het betreffende email-adres
     */
    @GetMapping("/deelnemr/{email}")
    Deelnemer haalDeelnemerOpEmailAdres(@PathVariable String email) {
        return this.repository.findByEmail(email);
    }

    /**
     * <strong>haalDeelnemerOpDeelnemersnummer(<i>String</i>)</strong><br>
     * Zoek een deelnemer op basis van diens deelnemersnummer.<br>
     * @param deelnemersnummer Het deelnemersnummer van de deelnemer waarnaar wordt gezocht.
     * @return De deelnemer met het betreffende email-adres
     */
    @GetMapping("/deelnemer/{deelnemersnummer}")
    Deelnemer haalDeelnemerOpDeelnemersnummer(@PathVariable String deelnemersnummer) {
        return this.repository.findByDeelnemersnummer(deelnemersnummer);
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
        Deelnemer deelnemer = repository.findByDeelnemersnummer(deelnemersnummer);

        // Bepaal de som de huidide beleggingen (-> stored procedure)
        double waardeHuidigeBeleggingen = repository.getSomBeleggingenDeelnemer(deelnemer.getId());

        // Bereken de premie en retourneer deze
        return (deelnemer.getPremieDeelnemer().getFullTimeSalaris() - deelnemer.getPremieDeelnemer().getFranciseActueel())
                * (deelnemer.getPremieDeelnemer().getParttimePercentage() / 100)
                * (deelnemer.getPremieDeelnemer().getPercentageBeschikbarePremie()/100);
    }





}
