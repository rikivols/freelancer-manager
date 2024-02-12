package fit.biktjv.freelancerManager.rest;

import fit.biktjv.freelancerManager.dataTransferObjects.FreelancerDTO;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<FreelancerDTO> getFreelancer(@RequestParam(value = "freelancerId", required = false) Long freelancerId) {
        if (freelancerId == null) {
            return freelancerDAO.getAllFreelancers().stream().map(Freelancer::toDTO).toList();
        }

        List<FreelancerDTO> freelancers = new ArrayList<>();
        freelancers.add(freelancerDAO.findFreelancer(freelancerId).toDTO());
        return freelancers;
    }
    @PostMapping
    public ResponseEntity create(@RequestBody FreelancerDTO freelancerDTO) {
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
       freelancerDAO.deleteFreelancer(freelancerId);

       Map<String, Object> response = new HashMap<>();
       response.put("details", "freelancer deleted successfully");
       response.put("freelancerId", freelancerId);
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/"+ freelancerId))
                .body(response);
    }
    Logger logger = LoggerFactory.getLogger(FreelancerRest.class);
}
