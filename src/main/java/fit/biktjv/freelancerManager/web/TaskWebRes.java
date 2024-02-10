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
        model.addAttribute("allTasks", freelancerDAO.getAllTasks());
        model.addAttribute("allFreelancers", freelancerDAO.getAllFreelancers());
        return "allTasks";
    }

    @GetMapping("for/{freelancerId}")
    public String tasksForFreelancerId(Model model, @PathVariable("freelancerId") Long freelancerId) {
        Freelancer freelancer = freelancerDAO.findFreelancer(freelancerId);
        model.addAttribute("freelancer", freelancer);
        model.addAttribute("tasksFor", freelancerDAO.tasksForFreelancerId(freelancerId));
        return "TasksForFreelancerId";
    }

    Logger logger = LoggerFactory.getLogger(TaskWebRes.class);


    @GetMapping("/create")
    public String create(Model model) {
        TaskForm taskForm = new TaskForm();
        taskForm.setPriority("Normal");
        taskForm.setTimeEstimated(null);
        model.addAttribute("taskForm", taskForm);
        return "addTask";
    }

    @GetMapping("/create/{freelancerId}")
    public String create(Model model, @PathVariable("freelancerId") Long freelancerId) {
        Freelancer freelancer = freelancerDAO.findFreelancer(freelancerId);
        model.addAttribute("taskForm", new TaskForm());
        model.addAttribute("freelancer", freelancer);
        return "addTask";
    }

//    @PostMapping("create")
//    public String post(@Valid TaskForm taskForm,
//                       @PathVariable(value = "freelancerId", required = false) Long freelancerIdPathVariable,
//                       BindingResult br) {
//        if (br.hasErrors())
//            return "addTask";
//        Long id = freelancerDAO.createTask(new Task(taskForm.getDsc(),
//                freelancerDAO.findFreelancer(freelancerId)));
//        return "redirect:/freelancer";
//    }

    @PostMapping("/createWithFreelancer")
    public String postWithFreelancer(@Valid TaskForm taskForm,
                                     @RequestParam("freelancerId") Long freelancerId,
                                     BindingResult br) {
        if (br.hasErrors())
            return "addTask";

        Freelancer freelancer = freelancerDAO.findFreelancer(freelancerId);
        Task task = new Task(taskForm);
        task.setFreelancer(freelancer);
        Long id = taskDAO.createTask(task);
        return "redirect:/freelancer";
    }

    @PostMapping("/createWithoutFreelancer")
    public String postWithoutFreelancer(@Valid TaskForm taskForm, BindingResult br) {
        if (br.hasErrors())
            return "addTask";

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
            taskDAO.saveTask(task);
        }

        return "redirect:/task";
    }

    @PostMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        freelancerDAO.deleteTask(id);
        return "redirect:/freelancer";
    }
}