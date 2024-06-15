package org.azhur.controllers;


import org.azhur.dto.CatDto;
import org.azhur.dto.OwnerDto;
import org.azhur.kafka.producers.KafkaCatDtoProducer;
import org.azhur.kafka.producers.KafkaIntegerProducer;
import org.azhur.models.CatUserModel;
import org.azhur.models.MyUser;
import org.azhur.services.CatRequestService;
import org.azhur.services.OwnerRequestService;
import org.azhur.utils.MappingUserModelsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interface-api/v1/cat")
public class CatInterface {
    private static final Logger log = LoggerFactory.getLogger(OwnerInterface.class);
    private final OwnerRequestService ownerRequestService;
    private final CatRequestService catRequestService;
    private final KafkaCatDtoProducer kafkaCatDtoProducer;
    private final KafkaIntegerProducer kafkaIntegerProducer;

    @Autowired
    public CatInterface(CatRequestService catRequestService,
                        KafkaCatDtoProducer kafkaCatDtoProducer,
                        KafkaIntegerProducer kafkaIntegerProducer,
                        OwnerRequestService ownerRequestService) {
        this.catRequestService = catRequestService;
        this.kafkaCatDtoProducer = kafkaCatDtoProducer;
        this.kafkaIntegerProducer = kafkaIntegerProducer;
        this.ownerRequestService = ownerRequestService;
    }
    @GetMapping("/get_all")
    public ResponseEntity<List<CatDto>> getAll() {
        return new ResponseEntity<>(getAvailableCats(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CatDto> get(@PathVariable int id) {
        CatDto catDto = catRequestService.getById(id);
        if (!ownerHasCat(catDto)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(catDto, HttpStatus.OK);
    }


    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody CatUserModel catUserModel) {
        CatDto catDto = MappingUserModelsUtil.toCatDto(catUserModel);
        OwnerDto ownerDto = ownerRequestService.getOwnerById(getUsersOwnerId());
        catDto.setOwner(ownerDto);
        kafkaCatDtoProducer.sendMessage("new-cats", catDto);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody CatDto catDto) {
        if (!ownerHasCat(catDto)) return new ResponseEntity<>("You don't have this cat", HttpStatus.NOT_FOUND);
        kafkaCatDtoProducer.sendMessage("new-cats", catDto);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        if (!ownerHasCat(catRequestService.getById(id))) return new ResponseEntity<>("You don't have this cat", HttpStatus.NOT_FOUND);
        kafkaIntegerProducer.sendMessage("cats_id", id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PutMapping("/add_friend/{id}/{friend_id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addFriend(@PathVariable int id, @PathVariable int friend_id) {
        CatDto catDto1 = catRequestService.getById(id);
        CatDto catDto2 = catRequestService.getById(friend_id);
        if (catDto1 == null || catDto2 == null) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
        catDto1.addFriend(catDto2);
        update(catDto1);
        return new ResponseEntity<>("Added friend", HttpStatus.OK);
    }

    @PutMapping("/set_owner/{id}/{owner_id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> setOwner(@PathVariable int id, @PathVariable int owner_id) {
        OwnerDto ownerDto = ownerRequestService.getOwnerById(owner_id);
        CatDto catDto = catRequestService.getById(id);
        if (catDto == null) {
            return new ResponseEntity<>("Not found cat", HttpStatus.NOT_FOUND);
        }
        if (ownerDto == null) {
            return new ResponseEntity<>("Not found owner", HttpStatus.NOT_FOUND);
        }
        catDto.setOwner(ownerDto);
        kafkaCatDtoProducer.sendMessage("new-cats", catDto);
        return new ResponseEntity<>("Owner is set", HttpStatus.OK);
    }

    private Authentication getCurrentAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private int getUsersOwnerId() {
        return ((MyUser) getCurrentAuthentication().getPrincipal()).getOwnerId();
    }

    private List<CatDto> getAvailableCats() {
        Authentication authentication = getCurrentAuthentication();
        String authority = authentication.getAuthorities().stream().toList().get(0).getAuthority();
        if (authority.equals("ROLE_ADMIN")) {
            return catRequestService.getAllCats();
        }

        int ownerId = getUsersOwnerId();

        return catRequestService.getCatsByOwner(ownerId);
    }
    private boolean ownerHasCat(CatDto catDto) {
        List<CatDto> cats = getAvailableCats();
        boolean ownerHasCat = false;
        for (CatDto value : cats) {
            if (catDto.getId() == value.getId()) {
                ownerHasCat = true;
                break;
            }
        }
        return ownerHasCat;
    }



}
