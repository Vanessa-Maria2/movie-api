package br.edu.ufrn.movieapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class IntegrationTMDBConfig {

    @Value("${api.key}")
    private String apiKey;

    private static String token;

    @Bean
    public CommandLineRunner integration() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        return args -> {
            if (token == null) {
                headers.add("Authorization", "Bearer " + apiKey);
                HttpEntity<String> request = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(
                        "https://api.themoviedb.org/3/authentication/token/new",
                        HttpMethod.GET,
                        request,
                        String.class
                );

                String json = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(json);
                token = jsonNode.get("request_token").asText();
            }
        };
    }

    public String getToken() {
        return token;
    }
}
