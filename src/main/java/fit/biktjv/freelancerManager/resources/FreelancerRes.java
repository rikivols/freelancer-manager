package fit.biktjv.freelancerManager.resources;

import fit.biktjv.freelancerManager.domain.Freelancer;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest/freelancer")
public class FreelancerRes {
    @Autowired // @Inject
    FreelancerDAO freelancerDAO;
    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping
    public List<Freelancer.FreelancerDTO> allFreelancer() {
        return freelancerDAO.allFreelancer().stream().map(Freelancer::toDTO).toList();
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Freelancer.FreelancerDTO freelancerDTO) {
        Long id = freelancerDAO.createFreelancer(new Freelancer(freelancerDTO));
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/"+ id))
                .build();
    }
    @DeleteMapping("{freelancerId}")
    public void delete(@PathVariable Long freelancerId) {
       freelancerDAO.deleteFreelancer(freelancerId);
    }
    Logger logger = LoggerFactory.getLogger(FreelancerRes.class);
}
