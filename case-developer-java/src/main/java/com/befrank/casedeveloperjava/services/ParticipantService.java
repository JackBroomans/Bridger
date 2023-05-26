package com.befrank.casedeveloperjava.services;

import com.befrank.casedeveloperjava.controlers.ParticipantController;
import com.befrank.casedeveloperjava.model.Participant;
import com.befrank.casedeveloperjava.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class ParticipantService {

    final ParticipantRepository repository;

    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    public List<Participant> getAllParticipants() {

        List participants = repository.findAll();


        return participants;
    }
}
