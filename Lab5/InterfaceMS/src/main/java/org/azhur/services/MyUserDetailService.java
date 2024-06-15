package org.azhur.services;

import lombok.RequiredArgsConstructor;
import org.azhur.dto.MyUserDto;
import org.azhur.kafka.producers.KafkaMyUserDtoProducer;
import org.azhur.models.MyUser;
import org.azhur.utils.MappingUserModelsUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final KafkaMyUserDtoProducer kafkaMyUserDtoProducer;
    private final UserRequestService userRequestService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDto userDto = userRequestService.getUserByEmail(username);
        Optional<MyUser> myUser = Optional.ofNullable(MappingUserModelsUtil.dtoToUser(userDto));
        if (myUser.isEmpty()) throw new UsernameNotFoundException(username);
        return myUser.get();
    }

    public void addUser(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        kafkaMyUserDtoProducer.sendMessage("new-users", MappingUserModelsUtil.userToDto(myUser));
    }

    public List<MyUserDto> findAll() {
        return userRequestService.getAllUsers();
    }
}

