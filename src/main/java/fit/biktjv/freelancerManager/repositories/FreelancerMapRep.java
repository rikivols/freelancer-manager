package fit.biktjv.freelancerManager.repositories;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.dataTransferObjects.FreelancerDTO;

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
        freelancerDTO.setFreelancerId(1L);
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

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList(tasks.values());
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

    @Override
    public List<Task> tasksForFreelancerId(Long freelancerId) {
        return getAllTasks().stream()
                .filter((task -> task.getFreelancer().getFreelancerId().equals(freelancerId))).toList();
    }

    @Override
    public void deleteTask(Long id) {
        tasks.remove(id);
    }
}
