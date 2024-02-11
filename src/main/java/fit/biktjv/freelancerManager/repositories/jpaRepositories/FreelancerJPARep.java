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
        // persist freelancer's address to Address database
        Address address = freelancer.getAddress();
        System.out.println("address: " + address);
        if (address != null) {
            em.persist(address);
        }

        System.out.println("still here");


        // persist freelancer's skills to Skill database
        for (Skill skill : freelancer.getSkills()) {
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
        Freelancer c = findFreelancer(id);
        em.remove(c);
    }

}
