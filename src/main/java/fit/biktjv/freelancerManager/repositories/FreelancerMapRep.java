package fit.biktjv.freelancerManager.repositories;

import fit.biktjv.freelancerManager.domain.Freelancer;
import fit.biktjv.freelancerManager.domain.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreelancerMapRep implements FreelancerDAO {

    public FreelancerMapRep() {
        init();
    }

    void init() {
        createFreelancer(new Freelancer("Tom"));
    }

    private Map<Long, Freelancer> freelancers = new HashMap<>();


    @Override
    public List<Freelancer> allFreelancer() {
        return new ArrayList<>(freelancers.values());
    }


    @Override
    public Long createFreelancer(Freelancer freelancer) {
        Long id = freelancers.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0l) + 1;
        freelancer.setId(id);
        freelancers.put(id, freelancer);
        return id;
    }

    @Override
    public void deleteFreelancer(Long id) {
        freelancers.remove(id);
    }

    @Override
    public Freelancer findFreelancer(Long freelancerId) {
        return freelancers.get(freelancerId);
    }

    private Map<Long, Task> tasks = new HashMap<>();


    @Override
    public List<Task> allTask() {
        return new ArrayList(tasks.values());
    }

    public Long createTask(Task task) {
        Long id = tasks.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0l) + 1;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public List<Task> tasksForFreelancerId(Long freelancerId) {
        return allTask().stream()
                .filter((task -> task.getFreelancer().getId().equals(freelancerId))).toList();
    }

    @Override
    public void deleteTask(Long id) {
        tasks.remove(id);
    }
}
