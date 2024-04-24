package org.chainoptim.shared.commonfeatures.location.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class GeocodingServiceImpl implements GeocodingService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${geocoding.api.key}")
    private String apiKey;

    public Double[] getCoordinates(String address) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                .queryParam("address", address)
                .queryParam("key", apiKey)
                .build()
                .encode()
                .toUri();

        GoogleResponse response = restTemplate.getForObject(uri, GoogleResponse.class);
        if (response != null && response.getStatus().equals("OK")) {
            GoogleResponse.Location location = response.getResults()[0].getGeometry().getLocation();
            return new Double[]{location.getLat(), location.getLng()};
        }
        return new Double[0];
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GoogleResponse {

        private Result[] results;
        private String status;

        @Data
        static class Result {
            private Geometry geometry;
        }

        @Data
        static class Geometry {
            private Location location;
        }

        @Data
        static class Location {
            private Double lat;
            private Double lng;
        }
    }

}
