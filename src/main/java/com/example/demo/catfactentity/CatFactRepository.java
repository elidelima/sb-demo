package com.example.demo.catfactentity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatFactRepository extends JpaRepository<CatFactEntity, Integer> {
}
