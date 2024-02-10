package fit.biktjv.freelancerManager.repositories;

import fit.biktjv.freelancerManager.entities.Task;
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
    public Long createTask(Task task) {
        em.persist(task);
        em.flush();
        return task.getTaskId();
    }

    @Override
    @Transactional
    public List<Task> getAllTasks() {
        TypedQuery<Task> q = em.createNamedQuery("allTasksQuery", Task.class);
        return q.getResultList();
    }
}
