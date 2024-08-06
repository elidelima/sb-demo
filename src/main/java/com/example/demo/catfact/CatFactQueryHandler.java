package com.example.demo.catfact;

import com.example.demo.Query;
import com.example.demo.catfactentity.CatFactEntity;
import com.example.demo.catfactentity.CatFactRepository;
import com.example.demo.exceptions.ExternalCatsFactsDownException;
import com.example.demo.exceptions.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatFactQueryHandler implements Query<Void, CatFactDTO> {

    private final RestTemplate restTemplate;

    private final CatFactRepository catFactRepository;

    public CatFactQueryHandler(RestTemplate restTemplate, CatFactRepository catFactRepository) {
        this.restTemplate = restTemplate;
        this.catFactRepository = catFactRepository;
    }

    @Override
    public ResponseEntity<CatFactDTO> execute(Void input) {
        CatFact catFact = getCatFact();
        CatFactDTO catFactDTO = new CatFactDTO(catFact.getFact());
        return ResponseEntity.ok(catFactDTO);
    }

    private CatFact getCatFact() {
        try {
            CatFact catFact = restTemplate.getForObject("https://catfact.ninja/fact", CatFact.class);
            catFactRepository.save(new CatFactEntity(catFact));
            return catFact;
        } catch (Exception exception) {
            throw new ExternalCatsFactsDownException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    new SimpleResponse("The external API is down! Try again later")
            );
        }
    }
}
