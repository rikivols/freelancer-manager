package fit.biktjv.freelancerManager;

import fit.biktjv.freelancerManager.repositories.TaskDAO;
import fit.biktjv.freelancerManager.repositories.jpaRepositories.TaskJPARep;
import fit.biktjv.freelancerManager.repositories.mockRepositories.TaskMapRep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {

    @Value("${fit.biktjv.rep}")
    String rep;

    @Bean
    public TaskDAO taskDAO() {
        System.out.println("PICKING REP FOR TASK: " + rep);
        return switch(rep) {
            case "MEM" -> new TaskMapRep();
            case "JPA" -> new TaskJPARep();
            default -> throw new RuntimeException("fail");
        };
    }
}