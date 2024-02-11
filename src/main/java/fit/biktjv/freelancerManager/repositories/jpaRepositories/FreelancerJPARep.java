package fit.biktjv.freelancerManager.repositories.jpaRepositories;

import fit.biktjv.freelancerManager.entities.Skill;
import fit.biktjv.freelancerManager.entities.Address;
import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;
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
    public void deleteFreelancer(Long id) {
        Freelancer freelancer = findFreelancer(id);

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
