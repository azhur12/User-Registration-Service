package org.azhur.services;

import org.azhur.dto.MyUserDto;
import org.azhur.jpaEntities.MyUserJpa;
import org.azhur.jpaEntities.Owner;
import org.azhur.repositories.MyUserRepository;
import org.azhur.repositories.OwnerRepository;
import org.azhur.utils.MappingDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserService {
    private static final Logger log = LoggerFactory.getLogger(MyUserService.class);

    private final MyUserRepository myUserRepository;
    private final OwnerRepository ownerRepository;

    public MyUserService(MyUserRepository myUserRepository, OwnerRepository ownerRepository) {
        this.myUserRepository = myUserRepository;
        this.ownerRepository = ownerRepository;
    }

    public void addUser(MyUserDto myUserDto) {
        log.info("Adding new user: {}", myUserDto);
        MyUserJpa myUser = MappingDtoUtil.dtoToUser(myUserDto);
        if (!ownerRepository.existsOwnerByPassportNumber(myUser.getOwner().getPassportNumber())) {
            log.info("Owner with passport number: {} does not exist. Saving owner.", myUser.getOwner().getPassportNumber());
            ownerRepository.save(myUser.getOwner());
        }
        myUserRepository.save(myUser);
        log.info("User added successfully");
    }

    public MyUserDto getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        MyUserJpa user = myUserRepository.findByEmail(email);
        if (user == null) {
            log.warn("User with email: {} not found", email);
            return null;
        }
        return MappingDtoUtil.userToDto(user);
    }

    public List<MyUserDto> getAllUsers() {
        log.info("Fetching all users");
        return myUserRepository.findAll().stream()
                .map(MappingDtoUtil::userToDto)
                .collect(Collectors.toList());
    }
}
