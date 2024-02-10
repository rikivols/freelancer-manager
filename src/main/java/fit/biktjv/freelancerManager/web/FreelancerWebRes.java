package fit.biktjv.freelancerManager.web;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/freelancer")
public class FreelancerWebRes {
    @Autowired // @Inject
    FreelancerDAO freelancerDAO;

    @GetMapping
    public String allFreelancer(Model model) {
        model.addAttribute("freelancers", freelancerDAO.getAllFreelancers());
        return "allFreelancer";
    }

    static class FreelancerForm {
        @Size(min = 3)
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @GetMapping("create")
    public String createinit(Model model) {
        model.addAttribute("freelancerForm", new FreelancerForm());
        return "addFreelancer";
    }

    @PostMapping("create")
    public String create(@Valid FreelancerForm freelancerForm, BindingResult br) {
        //  logger.debug(freelancerDTO.toString());
        if (br.hasErrors())
            return "addFreelancer";
//        Freelancer c = new Freelancer(freelancerForm);
//        //   logger.debug(c.toString());
//        Long id = freelancerDAO.createFreelancer(c);
        return "redirect:/freelancer";
    }

    @PostMapping("{freelancerId}")
    public String delete(@PathVariable Long freelancerId) {
        freelancerDAO.deleteFreelancer(freelancerId);
        return "redirect:/freelancer";
    }

    org.slf4j.Logger logger = LoggerFactory.getLogger(FreelancerWebRes.class);

}
