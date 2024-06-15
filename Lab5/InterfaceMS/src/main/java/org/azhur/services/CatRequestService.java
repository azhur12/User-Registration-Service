package org.azhur.services;

import org.azhur.dto.CatDto;
import org.azhur.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CatRequestService {
    private final RestTemplate restTemplate;

    @Autowired
    public CatRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CatDto getById(int id) {
        String url = "http://localhost:8082/cat-api/v1/get/" + id;
        return restTemplate.getForObject(url, CatDto.class);
    }

    public List<CatDto> getAllCats() {
        String url = "http://localhost:8082/cat-api/v1/get_all";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CatDto>>() {}
        ).getBody();
    }

    public List<CatDto> getCatsByOwner(int id) {
        String url = "http://localhost:8082/cat-api/v1/get_by_owner_id/" + id;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CatDto>>() {}
        ).getBody();
    }
}
