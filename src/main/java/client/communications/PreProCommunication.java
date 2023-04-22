package client.communications;

import client.models.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
public class PreProCommunication {
    private final RestTemplate restTemplate;

    private final String URL = "http://94.198.50.185:7081/api/users";

    public PreProCommunication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getHeader() {
        ResponseEntity<List<User>> response = restTemplate.exchange(URL, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return response.getHeaders().getFirst("Set-Cookie");
    }

    public String getCodePartOne(String setCookieHeader) {
        User jamesBrown = new User("James", "Brown", (byte)20);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Set-Cookie", setCookieHeader);

        RequestEntity<User> request = RequestEntity
                .post(URI.create(URL))
                .headers(headers)
                .body(jamesBrown);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        System.out.println("statusCode in partOne (response): " + response.getStatusCode());

        RequestEntity<Void> request2 = RequestEntity
                .get(URI.create(URL))
                .headers(headers)
                .build();
        ResponseEntity<List<User>> response2 = restTemplate.exchange(request2, new ParameterizedTypeReference<>() {});
        System.out.println("statusCode in partOne (response2): " + response2.getStatusCode());

        System.out.println("users in partOne: " + response2.getBody());
        return response.getBody();
    }

    public String getCodePartTwo(String setCookieHeader) {
        User thomasShelby = new User(3L, "Thomas", "Shelby", (byte)20);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Set-Cookie", setCookieHeader);
        RequestEntity<User> request = RequestEntity
                .put(URI.create(URL))
                .headers(headers)
                .body(thomasShelby);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getBody();
    }

    public String getCodePartThree(String setCookieHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Set-Cookie", setCookieHeader);
        RequestEntity<Void> request = RequestEntity
                .put(URI.create(URL + "/3"))
                .headers(headers)
                .build();
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response.getBody();
    }
}
