package fit.biktjv.freelancerManager.repositories;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
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
    public Long createFreelancer(Freelancer ent) {
        em.persist(ent);
        em.flush();
        return ent.getFreelancerId();
    }

    @Transactional
    public Freelancer findFreelancer(Long id) {
        return em.find(Freelancer.class, id);
    }

    @Override
    @Transactional
    public List<Task> tasksForFreelancerId(Long freelancerId) {
        TypedQuery<Task> tq = em.createNamedQuery("tasksForFreelancerIdQuery", Task.class);
        tq.setParameter("freelancerId", freelancerId);
        return tq.getResultList();
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = em.find(Task.class, id);
        em.remove(task);
    }

    @Override
    @Transactional
    public void deleteFreelancer(Long id) {
        Freelancer c = findFreelancer(id);
        em.remove(c);
    }

    @Transactional
    public List<Task> getAllTasks() {
        TypedQuery<Task> q = em.createNamedQuery("allTasksQuery", Task.class);
        return q.getResultList();
    }

    @Override
    @Transactional
    public Long createTask(Task ent) {
        em.persist(ent);
        em.flush();
        return ent.getTaskId();
    }

}
