package fit.biktjv.freelancerManager.repositories;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;

import java.util.List;

public interface FreelancerDAO {
    List<Freelancer> getAllFreelancers();

    Freelancer findFreelancer(Long freelancerId);

    Long createFreelancer(Freelancer ent);

    void deleteFreelancer(Long id);

    List<Task> getAllTasks();

    Long createTask(Task ent);

    List<Task> tasksForFreelancerId(Long freelancerId);

    void deleteTask(Long id);
}
