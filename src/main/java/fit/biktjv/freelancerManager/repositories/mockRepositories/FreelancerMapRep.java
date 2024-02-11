package fit.biktjv.freelancerManager.repositories.mockRepositories;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.dataTransferObjects.FreelancerDTO;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FreelancerMapRep implements FreelancerDAO {

    private Map<Long, Freelancer> freelancers = new HashMap<>();
    private Map<Long, Task> tasks = new HashMap<>();

    public FreelancerMapRep() {
        init();
    }

    void init() {
        FreelancerDTO freelancerDTO = new FreelancerDTO();
        freelancerDTO.setFirstName("Tom");
        freelancerDTO.setMiddleName("M");
        freelancerDTO.setLastName("Sawyer");
        freelancerDTO.setEmail("email@email.com");
        freelancerDTO.setPhoneNumber("+420 123456789");
        LocalDate birthday = LocalDate.parse("1990-04-04", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        freelancerDTO.setBirthday(birthday);
        freelancerDTO.setAdditionalInformation("Some additional information");

        createFreelancer(new Freelancer(freelancerDTO));
    }


    @Override
    public List<Freelancer> getAllFreelancers() {
        return new ArrayList<>(freelancers.values());
    }


    @Override
    public Long createFreelancer(Freelancer freelancer) {
        Long freelancerId = freelancers.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0l) + 1;
        freelancer.setFreelancerId(freelancerId);
        freelancers.put(freelancerId, freelancer);
        return freelancerId;
    }

    @Override
    public void deleteFreelancer(Long id) {
        freelancers.remove(id);
    }

    @Override
    public Freelancer findFreelancer(Long freelancerId) {
        return freelancers.get(freelancerId);
    }


    public Long createTask(Task task) {
        Long id = tasks.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0l) + 1;
        task.setTaskId(id);
        tasks.put(id, task);
        return id;
    }

}
