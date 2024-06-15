package org.azhur.services;

import org.azhur.jpaEntities.Breed;
import org.azhur.repositories.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BreedService {
    private final BreedRepository breedRepository;

    @Autowired
    public BreedService(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    public Breed saveBreed(Breed breed) {
        Optional<Breed> breed1 = breedRepository.findBreedByBreedName(breed.getBreedName());
        return breed1.orElseGet(() -> breedRepository.save(breed));
    }

    public Breed deleteBreedById(int id) {
        Breed breed = breedRepository.findById(id).get();
        breedRepository.deleteById(id);
        return breed;

    }
    public Optional<Breed> getBreedByName(String name) {
        return breedRepository.findBreedByBreedName(name);
    }

    public Optional<Breed> getBreed(int id) {
        return breedRepository.findById(id);
    }

    public List<Breed> getAll() {
        return breedRepository.findAll();
    }
}
