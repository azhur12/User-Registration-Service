package org.azhur.services;

import org.azhur.dto.OwnerDto;
import org.azhur.jpaEntities.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OwnerRequestService {

    private final RestTemplate restTemplate;

    @Autowired
    public OwnerRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OwnerDto getOwnerById(int id) {
        String url = "http://localhost:8081/owner-api/v1/get/" + id;
        return restTemplate.getForObject(url, OwnerDto.class);
    }

    public List<OwnerDto> getAllOwners() {
        String url = "http://localhost:8081/owner-api/v1/get_all";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OwnerDto>>() {}
        ).getBody();
    }

    public OwnerDto getOwnerByPassport(String passport) {
        String url = "http://localhost:8081/owner-api/v1/get_by_passport/" + passport;
        return restTemplate.getForObject(url, OwnerDto.class);
    }
}
