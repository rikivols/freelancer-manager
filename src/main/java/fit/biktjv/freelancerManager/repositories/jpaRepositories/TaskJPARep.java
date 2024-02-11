package fit.biktjv.freelancerManager.repositories.jpaRepositories;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.TaskDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class TaskJPARep implements TaskDAO {

    @Autowired
    EntityManager em;

    @Override
    @Transactional
    public Task findTask(Long taskId) {
        return em.find(Task.class, taskId);
    }

    @Override
    @Transactional
    public Long createTask(Task task) {
        em.persist(task);
        em.flush();
        return task.getTaskId();
    }

    @Override
    @Transactional
    public void saveTask(Task task) {
        em.merge(task);
    }

    @Override
    @Transactional
    public List<Task> getAllTasks() {
        TypedQuery<Task> q = em.createNamedQuery("allTasksQuery", Task.class);
        return q.getResultList();
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
    public void updateTask(Task task) {
        em.merge(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = em.find(Task.class, id);
        em.remove(task);
    }
}
