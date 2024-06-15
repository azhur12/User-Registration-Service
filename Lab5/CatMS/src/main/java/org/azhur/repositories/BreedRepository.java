package org.azhur.repositories;

import org.azhur.jpaEntities.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BreedRepository
        extends JpaRepository<Breed, Integer> {
    Optional<Breed> findBreedByBreedName(String name);
}
