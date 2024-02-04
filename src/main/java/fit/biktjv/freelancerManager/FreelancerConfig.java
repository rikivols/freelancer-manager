package fit.biktjv.freelancerManager;

import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import fit.biktjv.freelancerManager.repositories.FreelancerJPARep;
import fit.biktjv.freelancerManager.repositories.FreelancerMapRep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class FreelancerConfig {

    @Value("${fit.biktjv.rep}")
    String rep;
    @Bean
    public FreelancerDAO dao() {
        return switch(rep) {
            case "MEM" -> new FreelancerMapRep();
            case "JPA" -> new FreelancerJPARep();
            default -> throw new RuntimeException("fail");
        };
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("cz"));
        return slr;
    }
}
