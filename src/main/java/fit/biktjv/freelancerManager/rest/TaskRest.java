package fit.biktjv.freelancerManager.rest;

import fit.biktjv.freelancerManager.dataTransferObjects.TaskDTO;
import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import fit.biktjv.freelancerManager.repositories.TaskDAO;
import fit.biktjv.freelancerManager.web.bodies.AssignTaskBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.net.URI;

@RestController
@RequestMapping("/rest/task")
public class TaskRest {
    @Autowired // @Inject
    FreelancerDAO freelancerDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping("/all")
    public ResponseEntity allTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(
                taskDAO.getAllTasks().stream().map(Task::toDTO).toList()
        );
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TaskDTO taskDTO) {
        Freelancer freelancer = null;

        if (taskDTO.getFreelancerId() != null) {
            freelancer = freelancerDAO.findFreelancer(taskDTO.getFreelancerId());
        }
        Task task = new Task(taskDTO);
        task.setFreelancer(freelancer);
        task.setCreatedAt(LocalDateTime.now());
        Long taskId = taskDAO.createTask(task);

        Map<String, Object> response = new HashMap<>();
        response.put("details", "task inserted successfully");
        response.put("taskId", taskId);
        response.put("task", task);

        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/" + taskId))
                .body(response);
    }

    @PostMapping("/assignTask")
    public ResponseEntity<Map<String, Object>> assignTask(@RequestBody AssignTaskBody assignTaskBody) {
        Task task = taskDAO.findTask(assignTaskBody.getTaskId());
        Long freelancerId = assignTaskBody.getFreelancerId();

        Freelancer freelancer = null;

        if (freelancerId != -1) {
            freelancer = freelancerDAO.findFreelancer(freelancerId);
            if (freelancer == null) {
                return ResponseEntity.badRequest().body(Map.of("Response", "Freelancer not found"));
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("taskId", assignTaskBody.getTaskId());
        response.put("freelancerId", freelancerId);
        if (freelancerId != -1) {
            response.put("freelancerName", freelancer.toString());
        }

        if (task != null) {
            task.setFreelancer(freelancer);
            taskDAO.updateTask(task);
            response.put("Response", "Task assigned successfully");
            return ResponseEntity.ok(response);
        }

        response.put("Response", "Failed to assign task");
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity delete(@PathVariable Long taskId) {
        taskDAO.deleteTask(taskId);

        Map<String, Object> response = new HashMap<>();
        response.put("details", "task deleted successfully");
        response.put("taskId", taskId);
        return ResponseEntity.created(URI.create(httpServletRequest.getRequestURI() + "/"+ taskId))
                .body(response);
    }
}
