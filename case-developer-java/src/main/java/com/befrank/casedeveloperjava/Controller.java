package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/case")
public class Controller {
    @Autowired
    private DeelnemerRepository repository;

    @GetMapping
    public List<Integer> getUsers() {
        return repository.findAll()
                .stream()
                .map(Deelnemer::getId)
                .collect(Collectors.toList());
    }
}
