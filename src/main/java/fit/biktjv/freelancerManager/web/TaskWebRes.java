package fit.biktjv.freelancerManager.web;

import fit.biktjv.freelancerManager.web.bodies.AssignTaskBody;
import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import fit.biktjv.freelancerManager.repositories.TaskDAO;
import fit.biktjv.freelancerManager.web.forms.TaskForm;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskWebRes {
    @Autowired
    FreelancerDAO freelancerDAO;
    @Autowired
    TaskDAO taskDAO;

    @GetMapping
    public String all(Model model) {
        model.addAttribute("TasksGiven", taskDAO.getAllTasks());
        model.addAttribute("Title", "All Tasks");
        model.addAttribute("allFreelancers", freelancerDAO.getAllFreelancers());
        return "task/Tasks";
    }

    @GetMapping("/open")
    public String openTasks(Model model) {
        model.addAttribute("TasksGiven", taskDAO.getOpenTasks());
        model.addAttribute("Title", "Open Tasks");
        model.addAttribute("allFreelancers", freelancerDAO.getAllFreelancers());
        return "task/Tasks";
    }

    @GetMapping("/closed")
    public String closedTasks(Model model) {
        model.addAttribute("TasksGiven", taskDAO.getClosedTasks());
        model.addAttribute("Title", "Closed Tasks");
        model.addAttribute("allFreelancers", freelancerDAO.getAllFreelancers());
        return "task/Tasks";
    }

    @GetMapping("/unassigned")
    public String unassignedTasks(Model model) {
        model.addAttribute("TasksGiven", taskDAO.getUnassignedTasks());
        model.addAttribute("Title", "Unassigned Tasks");
        model.addAttribute("allFreelancers", freelancerDAO.getAllFreelancers());
        return "task/Tasks";
    }

    @GetMapping("/assigned")
    public String assignedTasks(Model model) {
        model.addAttribute("TasksGiven", taskDAO.getAssignedTasks());
        model.addAttribute("Title", "Assigned Tasks");
        model.addAttribute("allFreelancers", freelancerDAO.getAllFreelancers());
        return "task/Tasks";
    }

    @GetMapping("/for/{freelancerId}")
    public String tasksForFreelancerId(Model model, @PathVariable("freelancerId") Long freelancerId) {
        Freelancer freelancer = freelancerDAO.findFreelancer(freelancerId);
        model.addAttribute("freelancer", freelancer);
        model.addAttribute("tasksFor", taskDAO.tasksForFreelancerId(freelancerId));
        return "task/tasksForFreelancer";
    }

    @GetMapping("/details/{taskId}")
    public String taskDetails(@PathVariable("taskId") Long taskId, Model model) {
        Task task = taskDAO.findTask(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Invalid task Id: " + taskId.toString());
        }
        model.addAttribute("task", task);
        model.addAttribute("allFreelancers", freelancerDAO.getAllFreelancers());
        return "task/taskDetails";
    }

    @GetMapping("/modify/{taskId}")
    public String modify(@PathVariable("taskId") Long taskId, Model model) {
        Task task = taskDAO.findTask(taskId);
        TaskForm taskForm = new TaskForm(task);
        model.addAttribute("taskForm", taskForm);
        model.addAttribute("task", task);
        model.addAttribute("allFreelancers", freelancerDAO.getAllFreelancers());
        return "task/modifyTask";
    }

    Logger logger = LoggerFactory.getLogger(TaskWebRes.class);


    @GetMapping("/create")
    public String create(Model model, @RequestParam(value = "freelancerId", required = false) Long freelancerId) {
        Freelancer freelancer = null;
        if (freelancerId != null) {
            freelancer = freelancerDAO.findFreelancer(freelancerId);
        }
        TaskForm taskForm = new TaskForm();
        taskForm.setPriority("Normal");
        taskForm.setTimeEstimated(null);
        model.addAttribute("taskForm", taskForm);
        model.addAttribute("freelancer", freelancer);
        model.addAttribute("allFreelancers", freelancerDAO.getAllFreelancers());
        return "task/addTask";
    }

    @PostMapping("/createWithFreelancer")
    public String postWithFreelancer(@Valid TaskForm taskForm,
                                     @RequestParam("freelancerId") Long freelancerId,
                                     BindingResult br) {
        if (br.hasErrors())
            return "task/addTask";

        Freelancer freelancer = freelancerDAO.findFreelancer(freelancerId);
        Task task = new Task(taskForm);
        task.setFreelancer(freelancer);
        Long id = taskDAO.createTask(task);
        return "redirect:/freelancer/profile/" + freelancerId;
    }

    @PostMapping("/createWithoutFreelancer")
    public String postWithoutFreelancer(@Valid TaskForm taskForm, BindingResult br) {
        if (br.hasErrors())
            return "task/addTask";

        Task task = new Task(taskForm);
        Long id = taskDAO.createTask(task);
        return "redirect:/task";
    }

    @PostMapping("/assignTask")
    public String assignTask(@RequestBody AssignTaskBody assignTaskBody) {
        Task task = taskDAO.findTask(assignTaskBody.getTaskId());
        Freelancer freelancer = freelancerDAO.findFreelancer(assignTaskBody.getFreelancerId());

        if (task != null && freelancer != null) {
            task.setFreelancer(freelancer);
            taskDAO.updateTask(task);
        }

        return "redirect:/task";
    }

    @PostMapping("/modify/{taskId}")
    public String modify(@PathVariable("taskId") Long taskId, @Valid TaskForm taskForm, BindingResult br) {
        if (br.hasErrors())
            return "task/modify/" + taskId.toString();
        Task task = taskDAO.findTask(taskId);
        task.updateFromForm(taskForm);
        if (taskForm.getFreelancerId() != null) {
            Freelancer freelancer = freelancerDAO.findFreelancer(taskForm.getFreelancerId());
            task.setFreelancer(freelancer);
        }
        taskDAO.updateTask(task);
        return "redirect:/task/details/" + taskId;
    }

    @PostMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        taskDAO.deleteTask(id);
        return "redirect:/task";
    }
}