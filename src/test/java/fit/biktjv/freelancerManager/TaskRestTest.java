package fit.biktjv.freelancerManager;

import fit.biktjv.freelancerManager.dataTransferObjects.AddressDTO;
import fit.biktjv.freelancerManager.dataTransferObjects.FreelancerDTO;
import fit.biktjv.freelancerManager.dataTransferObjects.TaskDTO;
import fit.biktjv.freelancerManager.entities.Address;
import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import fit.biktjv.freelancerManager.repositories.TaskDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@TestPropertySource(properties = {"fit.biktjv.rep=MEM"})
class TaskRestTest {

    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    TaskDAO taskMapRep;

    @Autowired
    FreelancerDAO freelancerMapRep;

    Task task;

    TaskDTO taskDTO;

    Freelancer freelancer;

    FreelancerDTO freelancerDTO;

    HttpHeaders headers;

    @BeforeEach
    public void setUp() {
        taskMapRep.clear();
        freelancerMapRep.clear();

        freelancer = new Freelancer(
                "Tom", "Al", "Smith", "tom@gmail.com", "+420 123456789",
                LocalDate.parse("1999-05-04"), "Some additional information");
        Address address = new Address(1L, "Czech Republic", "Prague", "Vodickova", "1", "12345");
        freelancer.setAddress(address);
        freelancer.setSkills(List.of());
        freelancer.setFreelancerId(1L);

        freelancerDTO = new FreelancerDTO(freelancer);
        AddressDTO addressDTO = new AddressDTO(address);
        freelancerDTO.setAddress(addressDTO);
        freelancerDTO.setSkills(List.of());

        task = new Task(1L, "Test task", "Description of the task", "Need Info",
                "High", "1M", 500.0F, false);
        task.setCreatedAt(LocalDateTime.now());
        task.setFreelancer(freelancer);
        taskDTO = new TaskDTO(task);
        freelancerMapRep.createFreelancer(freelancer);

        String authenticationToken = "TOKEN";
        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authenticationToken);
    }

    @Test
    void authenticationMissingTest() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/task";

        ResponseEntity<String> response = restTemplate.getForEntity(freelancerRestUrl, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getAllTasksTest() {
        String root = "http://localhost:" + port;
        String taskRestUrl = root + "/rest/task/all";

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(taskRestUrl,
                HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void createTaskTestSuccess() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/task";

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(taskDTO.mapToJSON(), headers);

        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl, HttpMethod.POST,
                                                                entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        // make sure that it was added in the database
        Task newTask = taskMapRep.findTask(2L);
        assertThat(newTask).isNotNull();
        assertThat(newTask.getTaskId()).isEqualTo(2L);
        assertThat(newTask.getName()).isEqualTo(task.getName());
        assertThat(newTask.getDescription()).isEqualTo(task.getDescription());
        assertThat(newTask.getFreelancer().getFirstName()).isEqualTo(freelancer.getFirstName());
    }

    @Test
    void createTaskTestUnassigned() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/task";

        taskDTO.setFreelancerId(null);

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(taskDTO.mapToJSON(), headers);

        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl, HttpMethod.POST,
                entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        // make sure that it was added in the database
        Task newTask = taskMapRep.findTask(2L);
        assertThat(newTask).isNotNull();
        assertThat(newTask.getTaskId()).isEqualTo(2L);
        assertThat(newTask.getName()).isEqualTo(task.getName());
        assertThat(newTask.getDescription()).isEqualTo(task.getDescription());
        assertThat(newTask.getFreelancer()).isNull();
    }

    @Test
    void createTaskTestFailure() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/task";

        // Create a freelancer with incorrect data
        TaskDTO incorrectTask = new TaskDTO();
        incorrectTask.setName("");  // Empty task name

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(incorrectTask.mapToJSON(), headers);

        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl, HttpMethod.POST,
                entity, String.class);

        // Expect an error response
        assertThat(response.getStatusCode()).isNotEqualTo(HttpStatus.CREATED);
    }

    @Test
    void assignTaskTestSuccess() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/task/assignTask";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer TOKEN-PUBLIC");
        headers.setContentType(MediaType.APPLICATION_JSON);

        taskMapRep.findTask(1L).setFreelancer(null);

        String jsonString = "{\"taskId\":\"1\",\"freelancerId\":\"1\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl, HttpMethod.POST,
                entity, String.class);

        // Expect a successful response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        // task was assigned to the freelancer
        assertThat(taskMapRep.findTask(1L).getFreelancer().getFreelancerId()).isEqualTo(1L);
    }

    @Test
    void assignTaskTestUnassign() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/task/assignTask";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer TOKEN-PUBLIC");
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonString = "{\"taskId\":\"1\",\"freelancerId\":\"-1\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl, HttpMethod.POST,
                entity, String.class);

        // Expect a successful response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(taskMapRep.findTask(1L).getFreelancer()).isNull();
    }

    @Test
    void assignTaskTestFreelancerNotFound() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/task/assignTask";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer TOKEN-PUBLIC");
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonString = "{\"taskId\":\"1\",\"freelancerId\":\"5\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl, HttpMethod.POST,
                entity, String.class);

        // Expect a successful response
        assertThat(response.getStatusCode()).isNotEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        // no change was made
        assertThat(taskMapRep.findTask(1L).getFreelancer().getFreelancerId()).isEqualTo(1L);
    }

    @Test
    void assignTaskTestWrongBody() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/task/assignTask";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer TOKEN-PUBLIC");
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonString = "1";
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl, HttpMethod.POST,
                entity, String.class);

        // Expect a successful response
        assertThat(response.getStatusCode()).isNotEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        // no change was made
        assertThat(taskMapRep.findTask(1L).getFreelancer().getFreelancerId()).isEqualTo(1L);
    }

    @Test
    void deleteTaskTestSuccess() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/task";

        long taskId = 1L;

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl + "/" + taskId, HttpMethod.DELETE, entity, String.class);

        // Expect a successful response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Verify that the task was deleted
        Task deletedTask = taskMapRep.findTask(taskId);
        assertThat(deletedTask).isNull();
        assertThat(taskMapRep.getAllTasks()).isEmpty();
    }

    @Test
    void deleteTaskTestNotFound() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/task";

        long taskId = 9L; // this task does not exist

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        // Send a DELETE request to the endpoint
        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl + "/" + taskId, HttpMethod.DELETE, entity, String.class);

        assertThat(response.getStatusCode()).isNotEqualTo(HttpStatus.OK);
        assertThat(taskMapRep.getAllTasks().size()).isEqualTo(1);
    }
}
