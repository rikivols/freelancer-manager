package fit.biktjv.freelancerManager.repositories;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Skill;
import fit.biktjv.freelancerManager.entities.Task;

import java.util.List;

public interface FreelancerDAO {
    List<Freelancer> getAllFreelancers();

    Freelancer findFreelancer(Long freelancerId);

    Long createFreelancer(Freelancer freelancer);

    void updateFreelancer(Freelancer freelancer, List<Skill> skillsToDelete);

    void deleteFreelancer(Long id);
}
