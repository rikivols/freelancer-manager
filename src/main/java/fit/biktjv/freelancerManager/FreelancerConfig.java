package fit.biktjv.freelancerManager;

import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import fit.biktjv.freelancerManager.repositories.jpaRepositories.FreelancerJPARep;
import fit.biktjv.freelancerManager.repositories.mockRepositories.FreelancerMapRep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
