package org.azhur.controllers;

import org.azhur.dto.MyUserDto;
import org.azhur.services.MyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user-api/v1/user/")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final MyUserService myUserService;
    @Autowired
    public UserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @KafkaListener(topics = "new-users" , groupId = "usersGroup1",
    containerFactory = "myUserKafkaListenerContainerFactory")
    public void add(@RequestBody MyUserDto myUserDto) {
        log.info("Adding new user: {}", myUserDto);
        myUserService.addUser(myUserDto);
    }

    @GetMapping("get/{email}")
    public MyUserDto getUser(@PathVariable String email) {
        log.info("Fetching user with email {}", email);
        return myUserService.getUserByEmail(email);
    }

    @GetMapping("get_all")
    public List<MyUserDto> getAll() {
        log.info("Fetching all users");
        return myUserService.getAllUsers();
    }


}
