package org.azhur.services;

import lombok.RequiredArgsConstructor;
import org.azhur.dto.OwnerDto;
import org.azhur.jpaEntities.Owner;
import org.azhur.repositories.OwnerRepository;
import org.azhur.utils.MappingDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private static final Logger log = LoggerFactory.getLogger(OwnerService.class);
    private final OwnerRepository ownerRepository;

    public Optional<Owner> findOwner(int id) {
        log.info("Finding owner with ID: {}", id);
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isEmpty()) {
            log.warn("Owner with ID: {} not found", id);
        }
        return owner;
    }

    public Owner saveOwner(OwnerDto ownerDto) {
        log.info("Saving owner: {}", ownerDto);
        if (ownerRepository.existsOwnerByPassportNumber(ownerDto.getPassportNumber())) {
            log.info("Owner with passport number: {} already exists. Updating owner.", ownerDto.getPassportNumber());
            return updateOwner(ownerDto);
        } else {
            Owner savedOwner = ownerRepository.save(MappingDtoUtil.dtoToOwner(ownerDto));
            log.info("Owner saved with ID: {}", savedOwner.getId());
            return savedOwner;
        }
    }

    public Owner deleteOwner(OwnerDto ownerDto) {
        log.info("Deleting owner: {}", ownerDto);
        Owner owner = MappingDtoUtil.dtoToOwner(ownerDto);
        ownerRepository.delete(owner);
        log.info("Owner deleted with ID: {}", ownerDto.getId());
        return owner;
    }

    public Owner updateOwner(OwnerDto ownerDto) {
        log.info("Updating owner: {}", ownerDto);
        Optional<Owner> optionalOwner = ownerRepository.findOwnerByPassportNumber(ownerDto.getPassportNumber());
        Owner owner = optionalOwner.orElse(new Owner());
        Owner updatedOwner = ownerRepository.save(MappingDtoUtil.updateOwner(owner, ownerDto));
        log.info("Owner updated with ID: {}", updatedOwner.getId());
        return updatedOwner;
    }

    public Owner deleteOwnerById(int id) {
        log.info("Deleting owner with ID: {}", id);
        Optional<Owner> owner = ownerRepository.findOwnerById(id);
        if (owner.isEmpty()) {
            log.warn("Owner with ID: {} not found. Cannot delete.", id);
            return null;
        }
        ownerRepository.deleteById(id);
        log.info("Owner deleted with ID: {}", id);
        return owner.get();
    }

    public List<OwnerDto> findAllOwners() {
        log.info("Fetching all owners");
        return ownerRepository.findAll().stream()
                .map(MappingDtoUtil::ownerToDto)
                .collect(Collectors.toList());
    }

    public OwnerDto findOwnerByPassportNumber(String passportNumber) {
        log.info("Fetching owner by passport number: {}", passportNumber);
        Optional<Owner> optionalOwner = ownerRepository.findOwnerByPassportNumber(passportNumber);
        Owner owner = optionalOwner.orElse(null);
        return MappingDtoUtil.ownerToDto(owner);
    }
}
