package org.azhur.controllers;

import org.azhur.dto.OwnerDto;
import org.azhur.jpaEntities.Owner;
import org.azhur.services.OwnerService;
import org.azhur.utils.MappingDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owner-api/v1/")
public class OwnerController {
    private static final Logger log = LoggerFactory.getLogger(OwnerController.class);
    private final OwnerService ownerService;
    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping(path = "get_all")
    public ResponseEntity<List<OwnerDto>> getAll() {
        log.info("Fetching all owners");
        return new ResponseEntity<>(ownerService.findAllOwners(), HttpStatus.OK);
    }

    @KafkaListener(topics = "new-owners", groupId = "ownerGroup",
        containerFactory = "ownerKafkaListenerContainerFactory")
    public ResponseEntity<Owner> saveOwner(@RequestBody OwnerDto ownerDto) {
        log.info(String.format("Saving owner %s", ownerDto));
        return new ResponseEntity<>(ownerService.saveOwner(ownerDto), HttpStatus.OK);
    }

    @KafkaListener(topics = "owners_id", groupId = "owner_id_group",
         containerFactory = "integerKafkaListenerContainerFactory")
    public ResponseEntity<Owner> deleteOwnerById(@RequestBody Integer id) {
        Optional<Owner> owner = ownerService.findOwner(id);
        ownerService.deleteOwnerById(id);
        log.info(String.format("Deleting owner %s", id));
        return new ResponseEntity<>(owner.get(), HttpStatus.OK);

    }

    @GetMapping("get/{id}")
    public OwnerDto getOwnerById(@PathVariable Integer id) {
        log.info("Fetching owner with ID: {}", id);
        Optional<Owner> optionalOwner = ownerService.findOwner(id);
        Owner owner = optionalOwner.orElse(null);
        return MappingDtoUtil.ownerToDto(owner);
    }

    @GetMapping("/get_by_passport/{passport}")
    public OwnerDto getOwnerByEmail(@PathVariable String passport) {
        log.info("Fetching owner with passport: {}", passport);
        return ownerService.findOwnerByPassportNumber(passport);
    }
}
