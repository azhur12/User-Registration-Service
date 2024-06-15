package org.azhur.controllers;


import org.azhur.dto.CatDto;
import org.azhur.jpaEntities.Cat;
import org.azhur.services.BreedService;
import org.azhur.services.CatService;
import org.azhur.utils.MappingDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cat-api/v1/")
public class CatController {

    private final CatService catService;
    private final BreedService breedService;
    private static final Logger log = LoggerFactory.getLogger(CatController.class);

    public CatController(CatService catService, BreedService breedService) {
        this.catService = catService;
        this.breedService = breedService;
    }

    @KafkaListener(topics = "new-cats", groupId = "catsGroup",
            containerFactory = "catDtoKafkaListenerContainerFactory")
    public CatDto saveCat(CatDto catDto) {
        log.info("Saving new cat: {}", catDto);
        catService.saveCat(catDto);
        return catDto;
    }

    @KafkaListener(topics = "cats_id", groupId = "cats_group_id",
            containerFactory = "integerKafkaListenerContainerFactory")
    public Cat deleteCatById(Integer id) {
        log.info("Deleting cat with ID: {}", id);
        Optional<Cat> cat = catService.findCat(id);
        catService.deleteCatById(id);
        return cat.get();
    }

    @GetMapping("/get/{id}")
    public CatDto getCatById(@PathVariable Integer id) {
        log.info("Fetching cat with ID: {}", id);
        Optional<Cat> optionalCat = catService.findCat(id);
        Cat cat = optionalCat.orElse(null);
        return MappingDtoUtil.catToDto(cat);
    }

    @GetMapping("/get_all")
    public List<CatDto> getAll() {
        log.info("Fetching all cats");
        List<Cat> cats = catService.findAllCats();
        return cats.stream().map(MappingDtoUtil::catToDto).collect(Collectors.toList());
    }

    @GetMapping("/get_by_owner_id/{owner_id}")
    public List<CatDto> getByOwnerId(@PathVariable Integer owner_id) {
        log.info("Fetching cats for owner with ID: {}", owner_id);
        List<Cat> cats = catService.findByOwnerId(owner_id);
        return cats.stream().map(MappingDtoUtil::catToDto).collect(Collectors.toList());
    }
}
