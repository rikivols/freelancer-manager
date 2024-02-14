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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

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

    @GetMapping("modify/{freelancerId}")
    public String modify(Model model, @PathVariable Long freelancerId) {
        Freelancer freelancer = freelancerDAO.findFreelancer(freelancerId);
        FreelancerForm freelancerForm = new FreelancerForm(freelancer);

        model.addAttribute("freelancerForm", freelancerForm);

        // Format the LocalDate to String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = freelancer.getBirthday().format(formatter);
        model.addAttribute("formattedBirthday", formattedDate);
        model.addAttribute("freelancer", freelancerDAO.findFreelancer(freelancerId));
        return "freelancer/modifyFreelancer";
    }

    @PostMapping("modify/{freelancerId}")
    public String create(@PathVariable Long freelancerId, @Valid FreelancerForm freelancerForm, BindingResult br, Model model) {
        if (br.hasErrors()) {
            Freelancer freelancer = freelancerDAO.findFreelancer(freelancerId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = freelancer.getBirthday().format(formatter);
            model.addAttribute("formattedBirthday", formattedDate);
            model.addAttribute("freelancer", freelancer);
            return "freelancer/modifyFreelancer";
        }

        Freelancer freelancer = freelancerDAO.findFreelancer(freelancerId);
        freelancer.updateFromForm(freelancerForm);

        List<SkillForm> freelancerSkillForms = freelancerForm.getSkillForms();
        if (freelancerSkillForms == null) {
            freelancerSkillForms = new ArrayList<>();
        }

        // find deleted skills
        List<Skill> skillsToDelete = new ArrayList<>();

        for (Skill skill : freelancer.getSkills()) {
            boolean wasFound = false;

            for (SkillForm skillForm : freelancerSkillForms) {
                if (skill.getSkillId().equals(skillForm.getSkillId())) {
                    wasFound = true;
                    break;
                }
            }
            if (!wasFound) {
                skillsToDelete.add(skill);
            }
        }

        // Update skills or add new ones if they don't exist
        for (SkillForm skillForm : freelancerSkillForms) {
            // skill forms with null names are not valid
            if (skillForm.getName() == null || skillForm.getName().isEmpty()) {
                continue;
            }

            boolean wasFound = false;
            if (freelancer.getSkills() != null) {
                for (Skill skill : freelancer.getSkills()) {
                    if (skill.getSkillId() != null && skill.getSkillId().equals(skillForm.getSkillId())) {
                        skill.updateFromForm(skillForm);
                        wasFound = true;
                        break;
                    }
                }
            }

            if (!wasFound) {
                Skill skill = new Skill();
                skill.updateFromForm(skillForm);
                skill.setFreelancer(freelancer);
                freelancer.getSkills().add(skill);;
            }
        }

        // Remove deleted skills
        freelancer.getSkills().removeAll(skillsToDelete);

        freelancerDAO.updateFreelancer(freelancer, skillsToDelete);
        return "redirect:/freelancer/profile/" + freelancerId;
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
