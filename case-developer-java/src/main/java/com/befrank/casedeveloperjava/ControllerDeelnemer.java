package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/case")
public class ControllerDeelnemer {
    @Autowired
    private DeelnemerRepository repository;

    @GetMapping
    public List<Deelnemer> getDeelnemers() {
        return repository.findAll()
                .stream()
//                .map(Deelnemer::getId)
                .collect(Collectors.toList());
    }
}

