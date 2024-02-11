package fit.biktjv.freelancerManager.web;

import fit.biktjv.freelancerManager.entities.Skill;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.TaskDAO;
import fit.biktjv.freelancerManager.web.forms.FreelancerForm;
import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import fit.biktjv.freelancerManager.web.forms.SkillForm;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/freelancer")
public class FreelancerWebRes {
    @Autowired
    FreelancerDAO freelancerDAO;
    @Autowired
    TaskDAO taskDAO;

    @GetMapping
    public String allFreelancer(Model model) {
        model.addAttribute("freelancers", freelancerDAO.getAllFreelancers());
        return "freelancer/allFreelancers";
    }

    @GetMapping("create")
    public String createinit(Model model) {
        model.addAttribute("freelancerForm", new FreelancerForm());
        return "freelancer/addFreelancer";
    }

    @PostMapping("create")
    public String create(@Valid FreelancerForm freelancerForm, BindingResult br) {
        if (br.hasErrors())
            return "freelancer/addFreelancer";
        Freelancer freelancer = new Freelancer(freelancerForm);

        Long id = freelancerDAO.createFreelancer(freelancer);
        return "redirect:/freelancer";
    }

    @GetMapping("/profile/{freelancerId}")
    public String showFreelancerProfile(@PathVariable Long freelancerId, Model model) {
        Freelancer freelancer = freelancerDAO.findFreelancer(freelancerId);
        List<Task> tasks = taskDAO.tasksForFreelancerId(freelancerId);
        List<Task> tasksOpen = tasks.stream().filter(Task::isOpen).collect(Collectors.toList());
        List<Task> tasksClosed = tasks.stream().filter(task -> !task.isOpen()).collect(Collectors.toList());

        model.addAttribute("freelancer", freelancer);
        model.addAttribute("tasksOpen", tasksOpen);
        model.addAttribute("tasksClosed", tasksClosed);

        return "freelancer/freelancerProfile";
    }

    @PostMapping("{freelancerId}")
    public String delete(@PathVariable Long freelancerId) {
        freelancerDAO.deleteFreelancer(freelancerId);
        return "redirect:/freelancer";
    }

    org.slf4j.Logger logger = LoggerFactory.getLogger(FreelancerWebRes.class);

}
