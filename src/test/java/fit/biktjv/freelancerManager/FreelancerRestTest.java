package fit.biktjv.freelancerManager;

import fit.biktjv.freelancerManager.dataTransferObjects.AddressDTO;
import fit.biktjv.freelancerManager.dataTransferObjects.SkillDTO;
import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.dataTransferObjects.FreelancerDTO;

import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import fit.biktjv.freelancerManager.entities.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@TestPropertySource(properties = {"fit.biktjv.rep=MEM"})
class FreelancerRestTest {

    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    FreelancerDAO freelancerMapRep;

    Freelancer freelancer;

    FreelancerDTO freelancerDTO;

    Address address;

    AddressDTO addressDTO;

    @BeforeEach
    public void setUp() {
        freelancerMapRep.clear();

        freelancer = new Freelancer(
                "Tom", "Al", "Smith", "tom@gmail.com", "+420 123456789",
                LocalDate.parse("1999-05-04"), "Some additional information");
        address = new Address(1L, "Czech Republic", "Prague", "Vodickova", "1", "12345");
        freelancer.setAddress(address);
        freelancer.setSkills(List.of());

        freelancerDTO = new FreelancerDTO(freelancer);
        addressDTO = new AddressDTO(address);
        freelancerDTO.setAddress(addressDTO);
        freelancerDTO.setSkills(List.of());
    }

    @Test
    void getAllFreelancersTest() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/freelancer/all";

        ResponseEntity<String> response = restTemplate.getForEntity(freelancerRestUrl, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void createFreelancerTestSuccess() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/freelancer";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(freelancerDTO.mapToJSON(), headers);

        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl, HttpMethod.POST,
                                                                entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        // make sure that it was added in the database
        Freelancer newFreelancer = freelancerMapRep.findFreelancer(2L);
        assertThat(newFreelancer).isNotNull();
        assertThat(newFreelancer.getFirstName()).isEqualTo(freelancer.getFirstName());
        assertThat(newFreelancer.getAddress()).isNotNull();
        assertThat(newFreelancer.getAddress().getCity()).isEqualTo(freelancer.getAddress().getCity());
        assertThat(newFreelancer.getAddress().getAddressId()).isEqualTo(2L);
        assertThat(newFreelancer.getSkills()).isNotNull();
        assertThat(newFreelancer.getSkills()).isEmpty();
    }

    @Test
    void createFreelancerTestSkills() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/freelancer";

        freelancerDTO.setSkills(List.of(
                new SkillDTO("Java", 5, "Good", 2L),
                new SkillDTO("Python", 4, "Okay", 2L)
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(freelancerDTO.mapToJSON(), headers);

        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl, HttpMethod.POST,
                entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        // make sure that it was added in the database
        Freelancer newFreelancer = freelancerMapRep.findFreelancer(2L);
        assertThat(newFreelancer).isNotNull();
        assertThat(newFreelancer.getFirstName()).isEqualTo(freelancer.getFirstName());
        assertThat(newFreelancer.getAddress()).isNotNull();
        assertThat(newFreelancer.getAddress().getCity()).isEqualTo(freelancer.getAddress().getCity());
        assertThat(newFreelancer.getAddress().getAddressId()).isEqualTo(2L);
        assertThat(newFreelancer.getSkills()).isNotNull();
        assertThat(newFreelancer.getSkills().size()).isEqualTo(2);
        assertThat(newFreelancer.getSkills().get(0).getName()).isEqualTo("Java");
    }

    @Test
    void createFreelancerTestFailure() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/freelancer";

        // Create a freelancer with incorrect data
        FreelancerDTO incorrectFreelancer = new FreelancerDTO();
        incorrectFreelancer.setFirstName(""); // Empty first name
        incorrectFreelancer.setEmail("invalid email"); // Invalid email

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(incorrectFreelancer.mapToJSON(), headers);

        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl, HttpMethod.POST,
                entity, String.class);

        // Expect an error response
        assertThat(response.getStatusCode()).isNotEqualTo(HttpStatus.CREATED);
    }

    @Test
    void getFreelancerTestSuccess() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/freelancer";

        long freelancerId = 1L;

        ResponseEntity<String> response = restTemplate.getForEntity(freelancerRestUrl + "?freelancerId=" + freelancerId, String.class);

        // Expect a successful response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getFreelancerTestNotFound() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/freelancer";

        long freelancerId = 9L;

        ResponseEntity<String> response = restTemplate.getForEntity(freelancerRestUrl + "?freelancerId=" + freelancerId, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getFreelancerTestWrongDataType() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/freelancer";

        // Send a string instead of a long
        String freelancerId = "wrongDataType";

        ResponseEntity<String> response = restTemplate.getForEntity(freelancerRestUrl + "?freelancerId=" + freelancerId, String.class);

        // Expect a bad request response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getFreelancerTestAllFreelancers() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/freelancer";

        ResponseEntity<String> response = restTemplate.getForEntity(freelancerRestUrl, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void deleteFreelancerTestSuccess() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/freelancer";

        long freelancerId = 1L;

        // Send a DELETE request to the endpoint
        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl + "/" + freelancerId, HttpMethod.DELETE, null, String.class);

        // Expect a successful response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify that the freelancer was deleted
        Freelancer deletedFreelancer = freelancerMapRep.findFreelancer(freelancerId);
        assertThat(deletedFreelancer).isNull();
        assertThat(freelancerMapRep.getAllFreelancers()).isEmpty();
    }

    @Test
    void deleteFreelancerTestNotFound() {
        String root = "http://localhost:" + port;
        String freelancerRestUrl = root + "/rest/freelancer";

        long freelancerId = 9L; // this freelancer does not exist

        // Send a DELETE request to the endpoint
        ResponseEntity<String> response = restTemplate.exchange(freelancerRestUrl + "/" + freelancerId, HttpMethod.DELETE, null, String.class);

        assertThat(response.getStatusCode()).isNotEqualTo(HttpStatus.OK);
        assertThat(freelancerMapRep.getAllFreelancers().size()).isEqualTo(1);
    }
}
