package fit.biktjv.freelancerManager.rest;

import fit.biktjv.freelancerManager.dataTransferObjects.TaskDTO;
import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import fit.biktjv.freelancerManager.repositories.TaskDAO;
import fit.biktjv.freelancerManager.web.bodies.AssignTaskBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/task")
public class TaskRes {
    @Autowired // @Inject
    FreelancerDAO freelancerDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping
    public List<TaskDTO> allTask() {
       return taskDAO.getAllTasks().stream().map(Task::toDTO).toList();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TaskDTO taskDTO) {
        Freelancer freelancer = freelancerDAO.findFreelancer(taskDTO.getFreelancer().getFreelancerId());
        Task task = new Task(taskDTO);
        task.setFreelancer(freelancer);
        Long id = taskDAO.createTask(task);
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/" + id))
                .build();
    }

    @PostMapping("/assignTask")
    public ResponseEntity<Map<String, Object>> assignTask(@RequestBody AssignTaskBody assignTaskBody) {
        Task task = taskDAO.findTask(assignTaskBody.getTaskId());
        Long freelancerId = assignTaskBody.getFreelancerId();

        Freelancer freelancer = null;

        if (freelancerId != -1) {
            freelancer = freelancerDAO.findFreelancer(freelancerId);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("taskId", assignTaskBody.getTaskId());
        response.put("freelancerId", freelancerId);
        if (freelancerId != -1) {
            response.put("freelancerFirstName", freelancer.getFirstName());
            response.put("freelancerMiddleName", freelancer.getMiddleName());
            response.put("freelancerLastName", freelancer.getLastName());
        }

        if (task != null) {
            task.setFreelancer(freelancer);
            taskDAO.saveTask(task);
            response.put("Response", "Task assigned successfully");
            return ResponseEntity.ok(response);
        }

        response.put("Response", "Failed to assign task");
        return ResponseEntity.badRequest().body(response);
    }
}
