package com.example.demo.catfactentity;

import com.example.demo.catfact.CatFact;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cat_facts")
@AllArgsConstructor
@NoArgsConstructor
public class CatFactEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int ind;

    @Column(name = "cat_fact_JSON")
    private String catFactJSON;

    public CatFactEntity(CatFact catFact) {
        this.catFactJSON = convertToSON(catFact);
    }

    // TODO move to an utils file
    // Serialization
    private String convertToSON(CatFact catFact) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(catFact);
        } catch (Exception e) {
            throw new RuntimeException("JSON Parse error");
        }
    }

    //Deserialization
    public CatFact convertToCatFact() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(this.catFactJSON, CatFact.class);
        } catch (Exception e) {
            throw new RuntimeException("JSON Parse error");
        }
    }
}
