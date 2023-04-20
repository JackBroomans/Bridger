package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;

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
    List<Deelnemer> getDeelnemers() {
        return new ArrayList<>(this.repository.findAll());

    }

    /**
     * <strong>getDeelnemerOpId(<i>long</i>)</strong><br>
     * Haalt een deelnemer op uit de database op basis van de Id van die deelnemer.<br>
     * @param id Het kenmerk (id) van de deelnemer waarnaar wordt gezocht.
     * @return een instantie van de gezochte Deelnemer
     * @throws NoSuchElementException indien de deelnemer niet is gevonden.
     */
    @GetMapping("/deelnemer/{id}")
    Deelnemer getDeelnemerOpId(@PathVariable long id) {

        try {
            return this.repository.findById(id);
        }
        catch(Exception ex) {
            // Todo implement logging en log 'Deelnmeer niet gevonden op basis van id.'
            return null;
        }
    }

    @GetMapping("/deelnemer/{beleggingen}")
    public float getSomBeleggingenDeelnemer(long id) {
        return  repository.getSomBeleggingenDeelnemer(id);
    }

    @GetMapping("/dellnemers/{email}")
    Deelnemer getDeelnemerOpEmailAdres(@PathVariable String email) {

        try {
            Deelnemer deelnemer = repository.findByEmail(email);
            return deelnemer;
        }
        catch(Exception ex) {
            // Todo implement logging en log 'Deelnmeer niet gevonden op basis van id.'
            return null;
        }
    }




}
