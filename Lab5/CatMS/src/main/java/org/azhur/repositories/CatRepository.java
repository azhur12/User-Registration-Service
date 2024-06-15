package org.azhur.repositories;

import org.azhur.jpaEntities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository
        extends JpaRepository<Cat, Integer> {
    List<Cat> findCatByColor(String color);
    List<Cat> findCatByBreedId(long breedId);
    List<Cat> findCatByOwnerId(int ownerId);
}
