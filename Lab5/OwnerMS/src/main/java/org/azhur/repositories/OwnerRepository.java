package org.azhur.repositories;

import org.azhur.jpaEntities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository
        extends JpaRepository<Owner,Integer> {
    Optional<Owner> findOwnerById(int id);
    Optional<Owner> findOwnerByPassportNumber(String  passportNumber);
    boolean existsOwnerByPassportNumber(String passportNumber);
}
