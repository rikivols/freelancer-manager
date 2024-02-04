package fit.biktjv.freelancerManager.resources;

import fit.biktjv.freelancerManager.domain.Freelancer;
import fit.biktjv.freelancerManager.domain.Task;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest/task")
public class TaskRes {
    @Autowired // @Inject
    FreelancerDAO freelancerDAO;
    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping
    public List<Task.TaskDTO> allTask() {
       return freelancerDAO.allTask().stream().map(r->r.toDTO()).toList();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Task.TaskDTO taskDTO) {
        Freelancer freelancer = freelancerDAO.findFreelancer(taskDTO.freelancerId());
        Task task = new Task(taskDTO);
        task.setFreelancer(freelancer);
        Long id = freelancerDAO.createTask(task);
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/" + id))
                .build();
    }
}
