package fit.biktjv.freelancerManager.rest;

import fit.biktjv.freelancerManager.dataTransferObjects.FreelancerDTO;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/rest/freelancer")
public class FreelancerRest {
    @Autowired // @Inject
    FreelancerDAO freelancerDAO;
    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping("/all")
    public ResponseEntity getAllFreelancers() {
        return ResponseEntity.status(HttpStatus.OK).body(
            freelancerDAO.getAllFreelancers().stream().map(Freelancer::toDTO).toList()
        );
    }

    @GetMapping
    public ResponseEntity getFreelancer(@RequestParam(value = "freelancerId", required = false) Long freelancerId) {
        if (freelancerId == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                freelancerDAO.getAllFreelancers().stream().map(Freelancer::toDTO).toList()
            );
        }

        Freelancer freelancer = freelancerDAO.findFreelancer(freelancerId);
        if (freelancer == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Freelancer not found with id: " + freelancerId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        List<FreelancerDTO> freelancers = new ArrayList<>();
        freelancers.add(freelancerDAO.findFreelancer(freelancerId).toDTO());
        return ResponseEntity.status(HttpStatus.OK).body(
                freelancers
        );
    }
    @PostMapping
    public ResponseEntity create(@RequestBody FreelancerDTO freelancerDTO) {
        // Check if the address, firstName, or lastName is empty
        if (freelancerDTO.getAddress() == null || freelancerDTO.getFirstName().isEmpty() || freelancerDTO.getLastName().isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Address, first name, and last name must not be empty");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Freelancer freelancer = new Freelancer(freelancerDTO);
        Long freelancerId = freelancerDAO.createFreelancer(freelancer);

        Map<String, Object> response = new HashMap<>();
        response.put("details", "freelancer inserted successfully");
        response.put("freelancerId", freelancerId);
        response.put("freelancer", freelancer);
        response.put("address", freelancer.getAddress());
        response.put("skills", freelancer.getSkills());

        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/"+ freelancerId))
                .body(response);
    }
    @DeleteMapping("{freelancerId}")
    public ResponseEntity delete(@PathVariable Long freelancerId) {
        if (freelancerDAO.findFreelancer(freelancerId) == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Freelancer not found with id: " + freelancerId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        freelancerDAO.deleteFreelancer(freelancerId);

        Map<String, Object> response = new HashMap<>();
        response.put("details", "freelancer deleted successfully");
        response.put("freelancerId", freelancerId);

        URI location = URI.create(httpServletRequest.getRequestURI() + "/"+ freelancerId);
        return ResponseEntity.ok().location(location).body(response);
    }
    Logger logger = LoggerFactory.getLogger(FreelancerRest.class);
}
