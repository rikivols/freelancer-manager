package fit.biktjv.freelancerManager.repositories.jpaRepositories;

import fit.biktjv.freelancerManager.entities.Skill;
import fit.biktjv.freelancerManager.entities.Address;
import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
import fit.biktjv.freelancerManager.web.forms.FreelancerForm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class FreelancerJPARep implements FreelancerDAO {

    @Autowired
    EntityManager em;

    @Override

    @Transactional
    public List<Freelancer> getAllFreelancers() {
        TypedQuery<Freelancer> q = em.createNamedQuery("allFreelancersQuery", Freelancer.class);
        return q.getResultList();
    }

    @Override
    @Transactional
    public Long createFreelancer(Freelancer freelancer) {
        Address address = freelancer.getAddress();
        if (address != null) {
            em.persist(address);
        }

        for (Skill skill : freelancer.getSkills()) {
            skill.setFreelancer(freelancer);
            em.persist(skill);
        }

        em.persist(freelancer);

        em.flush();
        return freelancer.getFreelancerId();
    }

    @Transactional
    public Freelancer findFreelancer(Long id) {
        return em.find(Freelancer.class, id);
    }

    @Override
    @Transactional
    public void updateFreelancer(Freelancer freelancer, List<Skill> skillsToDelete) {

        // Merge the address associated with the freelancer
        Address address = freelancer.getAddress();
        if (address != null) {
            em.merge(address);
        }

        for (Skill skillToDelete : skillsToDelete) {
            em.remove(skillToDelete);
        }

        // Merge all skills associated with the freelancer
        for (Skill skill : freelancer.getSkills()) {
            em.merge(skill);
        }

        em.merge(freelancer);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Don't delete the database :(");
    }

    @Override
    @Transactional
    public void deleteFreelancer(Long freelancerId) {
        Freelancer freelancer = findFreelancer(freelancerId);

        // Remove all tasks associated with the freelancer
        for (Task task : freelancer.getTasks()) {
            em.remove(task);
        }

        // Remove all skills associated with the freelancer
        for (Skill skill : freelancer.getSkills()) {
            em.remove(skill);
        }

        // Remove the address associated with the freelancer
        Address address = freelancer.getAddress();
        if (address != null) {
            em.remove(address);
        }

        em.remove(freelancer);
    }

}
