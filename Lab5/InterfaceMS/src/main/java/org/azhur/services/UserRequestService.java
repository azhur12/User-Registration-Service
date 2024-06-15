package org.azhur.services;

import org.azhur.dto.MyUserDto;
import org.azhur.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserRequestService {
    private final RestTemplate restTemplate;

    @Autowired
    public UserRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MyUserDto getUserByEmail(String email) {
        String url = "http://localhost:8081/user-api/v1/user/get/" + email;
        return restTemplate.getForObject(url, MyUserDto.class);
    }

    public List<MyUserDto> getAllUsers() {
        String url = "http://localhost:8081/user-api/v1/user/get_all";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MyUserDto>>() {}
        ).getBody();
    }
}
