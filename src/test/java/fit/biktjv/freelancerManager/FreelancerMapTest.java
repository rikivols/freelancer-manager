package fit.biktjv.freelancerManager.repositories.mockRepositories;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(properties = "spring.profiles.active=test")
public class FreelancerMapTest {

    @Autowired
    FreelancerDAO freelancerMapRep;
    Freelancer freelancer;

    @BeforeEach
    public void setUp() {
        freelancerMapRep.clear();
        freelancer = new Freelancer(
                "Tom", "Al", "Smith", "tom@gmail.com", "+420 123456789",
                LocalDate.parse("1999-05-04"), "Some additional information");
    }

    @Test
    public void testCreateFreelancer() {
        freelancer.setFreelancerId(2L);
        freelancerMapRep.createFreelancer(freelancer);
        assertEquals(freelancer, freelancerMapRep.findFreelancer(2L));
    }

    @Test
    public void testFindFreelancerSuccess() {
        Freelancer freelancer = freelancerMapRep.findFreelancer(1L);
        assertEquals(freelancer.getFreelancerId(), 1L);
        assertEquals(freelancer.getFirstName(), "Tom");
        assertEquals(freelancer.getMiddleName(), "M");
        assertEquals(freelancer.getLastName(), "Sawyer");
        assertEquals(freelancer.getAdditionalInformation(), "Some additional information");
        assertEquals(freelancer.getAddress().getAddressId(), 1L);
        assertEquals(freelancer.getAddress().getCity(), "Los Angeles");
        assertEquals(freelancer.getSkills().size(), 1);
        assertEquals(freelancer.getSkills().get(0).getName(), "Java");
        assertEquals(freelancer.getSkills().get(0).getSkillId(), 1L);
    }

    @Test
    public void testFindFreelancerFailure() {
        Freelancer freelancer = freelancerMapRep.findFreelancer(3L);
        assertNull(freelancer);
    }

    @Test
    public void testUpdateFreelancer() {
        Freelancer freelancer = new Freelancer();
        freelancer.setFreelancerId(2L);
        freelancerMapRep.createFreelancer(freelancer);
        freelancer.setFirstName("New name");
        freelancerMapRep.updateFreelancer(freelancer, List.of());
        assertEquals("New name", freelancerMapRep.findFreelancer(2L).getFirstName());
    }

    @Test
    public void testDeleteFreelancerSuccess() {
        freelancerMapRep.deleteFreelancer(1L);
        assertNull(freelancerMapRep.findFreelancer(1L));
    }

    @Test
    public void testDeleteFreelancerNotFound() {
        freelancerMapRep.deleteFreelancer(5L);
        Freelancer freelancer = freelancerMapRep.findFreelancer(1L);
        assertEquals(freelancer.getFreelancerId(), 1L);
        assertEquals(freelancerMapRep.getAllFreelancers().size(), 1);
    }

    @Test
    public void testGetAllFreelancers() {
        assertEquals(freelancerMapRep.getAllFreelancers().size(), 1);
    }

    @Test
    public void testGetAllFreelancersAdd() {
        freelancer.setFreelancerId(2L);
        freelancerMapRep.createFreelancer(freelancer);
        assertEquals(freelancerMapRep.getAllFreelancers().size(), 2);
    }

    @Test
    public void testGetAllFreelancersEmpty() {
        freelancerMapRep.deleteFreelancer(1L);
        assertEquals(freelancerMapRep.getAllFreelancers().size(), 0);
    }
}