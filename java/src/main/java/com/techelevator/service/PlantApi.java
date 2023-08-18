package com.techelevator.service;

import com.techelevator.model.Plant;
import com.techelevator.model.PlantDto;
import com.techelevator.model.PlantSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class PlantApi {

    private String apiKey;
    private final String BASE_URL = "https://perenual.com/api";
    private final String PLANT_DETAILS = "/species/details/";
    private final RestTemplate restTemplate = new RestTemplate();


    public PlantApi(@Value("${plants.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    // /species/details/${id}?key=${process.env.VUE_APP_PLANTS_API_KEY}
    public Plant getPlantById(int id) {
        Plant plant = new Plant();
        HttpEntity<String> entity = makeEntityForGet();
        try {
            ResponseEntity<Plant> response =
                    restTemplate
                            .exchange(BASE_URL + PLANT_DETAILS + id + "?key=" + apiKey,
                                    HttpMethod.GET,
                                    entity,
                                    new ParameterizedTypeReference<Plant>() {});
            plant = response.getBody();
        } catch (RestClientResponseException e) {
            System.err.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.err.println(e.getMessage());
        }
        return plant;
    }

    // /species-list?key=${process.env.VUE_APP_PLANTS_API_KEY}&q=${plantName}

    public List<Plant> getPlantsBySearch(String searchWord) {
        List<Plant> plants = new ArrayList<>();
        if (searchWord.endsWith("s")) {
            searchWord = searchWord.substring(0, searchWord.length() - 1);
        }
        PlantSearchResponse plantSearchResponse = new PlantSearchResponse();
        HttpEntity<String> entity = makeEntityForGet();
        try {
            ResponseEntity<PlantSearchResponse> response =
                    restTemplate
                            .exchange(BASE_URL + "/species-list?key=" + apiKey + "&q=" + searchWord,
                                    HttpMethod.GET,
                                    entity,
                                    new ParameterizedTypeReference<PlantSearchResponse>() {});
            plantSearchResponse = response.getBody();
        } catch (RestClientResponseException e) {
            System.err.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.err.println(e.getMessage());
        }
        if (plantSearchResponse != null) {
            for (Plant plant : plantSearchResponse.getData()) {
                plants.add(getPlantById(plant.getId()));
            }
        }
        return plants;
    }

    public PlantDto getPlantByIdForGarden(int id) {
        PlantDto plant = new PlantDto();
        HttpEntity<String> entity = makeEntityForGet();
        try {
            ResponseEntity<PlantDto> response =
                    restTemplate
                            .exchange(BASE_URL + PLANT_DETAILS + id + "?key=" + apiKey,
                                    HttpMethod.GET,
                                    entity,
                                    new ParameterizedTypeReference<PlantDto>() {});
            plant = response.getBody();
        } catch (RestClientResponseException e) {
            System.err.println(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.err.println(e.getMessage());
        }
        return plant;
    }

    private HttpEntity<String> makeEntityForGet() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>("", headers);
    }
}
