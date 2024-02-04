package fit.biktjv.freelancerManager.web;

import fit.biktjv.freelancerManager.domain.Freelancer;
import fit.biktjv.freelancerManager.domain.Task;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
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
    FreelancerDAO freelancersDAO;

    @GetMapping
    public String all(Model model) {
        model.addAttribute("allTask", freelancersDAO.allTask());
        return "allTasks";
    }

    @GetMapping("for/{freelancerId}")
    public String tasksForFreelancerId(Model model, @PathVariable("freelancerId") Long freelancerId) {
        Freelancer freelancer = freelancersDAO.findFreelancer(freelancerId);
        model.addAttribute("freelancer", freelancer);
        model.addAttribute("tasksFor", freelancersDAO.tasksForFreelancerId(freelancerId));
        return "TasksForFreelancerId";
    }

    Logger logger = LoggerFactory.getLogger(TaskWebRes.class);

    static class TaskForm {
        String dsc;

        public String getDsc() {
            return dsc;
        }

        public void setDsc(String dsc) {
            this.dsc = dsc;
        }
    }

    @GetMapping("create")
    public String create(Model model, @RequestParam("freelancerId") Long freelancerId) {
        Freelancer freelancer = freelancersDAO.findFreelancer(freelancerId);
        model.addAttribute("taskForm", new TaskForm());
        model.addAttribute("freelancer", freelancer);
        return "addTask";
    }

    @PostMapping("create")
    public String post(@Valid TaskForm taskForm,
                       @RequestParam("freelancerId") Long freelancerId, BindingResult br) {
        if (br.hasErrors())
            return "addTask";
        Long id = freelancersDAO.createTask(new Task(taskForm.getDsc(),
                freelancersDAO.findFreelancer(freelancerId)));
        return "redirect:/freelancer";
    }

    @PostMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        freelancersDAO.deleteTask(id);
        return "redirect:/freelancer";
    }
}