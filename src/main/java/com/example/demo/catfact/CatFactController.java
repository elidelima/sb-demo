package com.example.demo.catfact;

import com.example.demo.catfactentity.CatFactEntity;
import com.example.demo.catfactentity.CatFactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catfacts")
public class CatFactController {

    @Autowired CatFactQueryHandler catFactQueryHandler;

    @Autowired CatFactRepository catFactRepository;

    @GetMapping
    public ResponseEntity<CatFactDTO> getCatFact() {
        return catFactQueryHandler.execute(null);
    }

    @GetMapping("/local")
    public ResponseEntity<List<CatFact>> getSavedCatFacts() {
        return ResponseEntity.ok(catFactRepository
                .findAll()
                .stream()
                .map(CatFactEntity::convertToCatFact)
                .collect(Collectors.toList())
        );
    }
}
