package client.communications;

import client.models.Employee;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TregulovCommunication {
    private final RestTemplate restTemplate;

    private final String URL = "http://localhost:8080/api/employees";

    public TregulovCommunication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Employee> getAllEmployees() {
        ResponseEntity<List<Employee>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
        return responseEntity.getBody();
    }

    public Employee getEmployee(long id) {
        return restTemplate.getForObject(String.format("%s/%d", URL, id), Employee.class);
    }

    public void addOrUpdateEmployee(Employee employee) {
        long id = employee.getId();
        if (id == 0) {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, employee, String.class);
            System.out.println("New employee was added to the database:\n" + responseEntity.getBody());
        } else {
            restTemplate.put(URL, employee);
            System.out.printf("Employee with id %d was updated:\n%s\n", id, employee);
        }
    }

    public void deleteEmployee(long id) {
        restTemplate.delete(String.format("%s/%d", URL, id));
        System.out.printf("Employee with id %d was removed from the database\n", id);
    }
}
