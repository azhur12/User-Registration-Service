package org.azhur.controllers;

import org.azhur.dto.OwnerDto;
import org.azhur.kafka.producers.KafkaIntegerProducer;
import org.azhur.kafka.producers.KafkaOwnerDtoProducer;
import org.azhur.models.OwnerUserModel;
import org.azhur.services.OwnerRequestService;
import org.azhur.utils.MappingUserModelsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interface-api/v1/owner")
public class OwnerInterface {
    private static final Logger log = LoggerFactory.getLogger(OwnerInterface.class);
    private final KafkaOwnerDtoProducer kafkaOwnerDtoProducer;
    private final KafkaIntegerProducer kafkaIntegerProducer;
    private final OwnerRequestService ownerRequestService;
    @Autowired
    public OwnerInterface(KafkaOwnerDtoProducer kafkaOwnerDtoProducer,
                          KafkaIntegerProducer kafkaIntegerProducer,
                          OwnerRequestService ownerRequestService) {
        this.kafkaOwnerDtoProducer = kafkaOwnerDtoProducer;
        this.kafkaIntegerProducer = kafkaIntegerProducer;
        this.ownerRequestService = ownerRequestService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> sendMessageSave(@RequestBody OwnerUserModel ownerUserModel) {
        kafkaOwnerDtoProducer.sendMessage("new-owners", MappingUserModelsUtil.toOwnerDto(ownerUserModel));
        return ResponseEntity.ok("Successfully saved");
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> sendMessageUpdate(@RequestBody OwnerUserModel ownerUserModel) {
        kafkaOwnerDtoProducer.sendMessage("new-owners", MappingUserModelsUtil.toOwnerDto(ownerUserModel));
        return ResponseEntity.ok("Successfully updated");
    }

    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> sendMessageDeleteById(@PathVariable int id) {
        kafkaIntegerProducer.sendMessage("owners_id", id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable int id) {
        OwnerDto ownerDto = ownerRequestService.getOwnerById(id);
        if (ownerDto != null) {
            return ResponseEntity.ok(ownerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get_all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        List<OwnerDto> owners = ownerRequestService.getAllOwners();
        return ResponseEntity.ok(owners);
    }
}
