package fit.biktjv.freelancerManager.web;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import fit.biktjv.freelancerManager.repositories.TaskDAO;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class IndexWebRes {
    @Autowired
    LocaleResolver localeResolver;
    @Autowired
    HttpServletRequest request;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    FreelancerDAO freelancerDAO;

    @GetMapping("/")
    public String home(Model model) {
        List<Task> allTasks = taskDAO.getAllTasks();
        List<Task> recentTasks = allTasks.stream()
                .sorted(Comparator.comparing(Task::getCreatedAt).reversed())
                .limit(5)
                .toList();
        model.addAttribute("recentTasks", recentTasks);
        List<Task> unpaidTasks = allTasks.stream()
                .filter(task -> !task.isOpen() && !task.getPaid()).toList();
        model.addAttribute("unpaidTasks", unpaidTasks);
        model.addAttribute("allFreelancers", freelancerDAO.getAllFreelancers());
        return "index";
    }

    @PostMapping("locale")
    public String locale(@RequestParam("locale") String locale) {
        localeResolver.setLocale(request, null, new Locale(locale));
        return "redirect:/";
    }
}
