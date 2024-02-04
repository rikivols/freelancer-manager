package fit.biktjv.freelancerManager.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Controller
@RequestMapping("/")
public class IndexWebRes {
    @Autowired
    LocaleResolver localeResolver;
    @Autowired
    HttpServletRequest request;

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("locale")
    public String locale(@RequestParam("locale") String locale) {
        localeResolver.setLocale(request, null, new Locale(locale));
        return "redirect:/";
    }
}
