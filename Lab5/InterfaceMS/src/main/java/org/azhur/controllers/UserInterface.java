package org.azhur.controllers;

import org.azhur.models.MyUser;
import org.azhur.services.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("interface-api/v1/users")
public class UserInterface {
    private final MyUserDetailService myUserDetailService;

    @Autowired
    public UserInterface(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody MyUser user) {
        myUserDetailService.addUser(user);
        return ResponseEntity.ok("New user created");
    }
}
