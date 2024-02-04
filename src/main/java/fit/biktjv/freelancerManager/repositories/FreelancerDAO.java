package fit.biktjv.freelancerManager.repositories;

import fit.biktjv.freelancerManager.domain.Freelancer;
import fit.biktjv.freelancerManager.domain.Task;

import java.util.List;

public interface FreelancerDAO {
    List<Freelancer> allFreelancer();

    Freelancer findFreelancer(Long freelancerId);

    Long createFreelancer(Freelancer ent);

    void deleteFreelancer(Long id);

    List<Task> allTask();

    Long createTask(Task ent);

    List<Task> tasksForFreelancerId(Long freelancerId);

    void deleteTask(Long id);
}
