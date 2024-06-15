package org.azhur.services;

import org.azhur.dto.CatDto;
import org.azhur.jpaEntities.Cat;
import org.azhur.repositories.CatRepository;
import org.azhur.utils.MappingDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {
    private static final Logger log = LoggerFactory.getLogger(CatService.class);

    private final CatRepository catRepository;
    private final BreedService breedService;

    @Autowired
    public CatService(CatRepository catRepository, BreedService breedService) {
        this.catRepository = catRepository;
        this.breedService = breedService;
    }

    public Optional<Cat> findCat(int id) {
        log.info("Finding cat with ID: {}", id);
        return catRepository.findById(id);
    }

    public Cat saveCat(CatDto catDto) {
        log.info("Saving cat: {}", catDto);
        if (catRepository.findById(catDto.getId()).isPresent()) {
            log.info("Cat with ID: {} already exists. Updating cat.", catDto.getId());
            return updateCat(catDto);
        } else {
            Cat cat = MappingDtoUtil.dtoToCat(catDto);
            if (breedService.getBreedByName(cat.getBreed().getBreedName()).isPresent()) {
                cat.setBreed(breedService.getBreedByName(cat.getBreed().getBreedName()).get());
            } else {
                log.info("Breed: {} not found. Saving new breed.", cat.getBreed().getBreedName());
                breedService.saveBreed(cat.getBreed());
            }
            Cat savedCat = catRepository.save(cat);
            log.info("Cat saved with ID: {}", savedCat.getId());
            return savedCat;
        }
    }

    public Cat deleteCat(CatDto catDto) {
        log.info("Deleting cat: {}", catDto);
        Cat cat = MappingDtoUtil.dtoToCat(catDto);
        if (catRepository.findById(catDto.getId()).isEmpty()) {
            log.warn("Cat with ID: {} not found. Cannot delete.", catDto.getId());
            return null;
        }
        catRepository.delete(cat);
        log.info("Cat deleted with ID: {}", catDto.getId());
        return cat;
    }

    public Cat deleteCatById(int id) {
        log.info("Deleting cat with ID: {}", id);
        if (catRepository.findById(id).isEmpty()) {
            log.warn("Cat with ID: {} not found. Cannot delete.", id);
            return null;
        }
        Cat cat = catRepository.findById(id).orElse(null);
        catRepository.deleteById(id);
        log.info("Cat deleted with ID: {}", id);
        return cat;
    }

    public Cat updateCat(CatDto catDto) {
        log.info("Updating cat: {}", catDto);
        Optional<Cat> optionalCat = catRepository.findById(catDto.getId());
        Cat cat = optionalCat.orElse(new Cat());
        Cat updatedCat = catRepository.save(MappingDtoUtil.updateCat(cat, catDto));
        log.info("Cat updated with ID: {}", updatedCat.getId());
        return updatedCat;
    }

    public List<Cat> findAllCats() {
        log.info("Fetching all cats");
        return catRepository.findAll();
    }

    public List<Cat> findByBreedId(int breedId) {
        log.info("Fetching cats with breed ID: {}", breedId);
        return catRepository.findCatByBreedId(breedId);
    }

    public List<Cat> findByColor(String color) {
        log.info("Fetching cats with color: {}", color);
        return catRepository.findCatByColor(color);
    }

    public List<Cat> findByOwnerId(int id) {
        log.info("Fetching cats for owner with ID: {}", id);
        return catRepository.findCatByOwnerId(id);
    }
}
