package fit.biktjv.freelancerManager.repositories;

import fit.biktjv.freelancerManager.entities.Task;

import java.util.List;

public interface TaskDAO {

    Task findTask(Long taskId);
    Long createTask(Task task);

    void saveTask(Task task);
    List<Task> getAllTasks();

    List<Task> tasksForFreelancerId(Long freelancerId);

    void updateTask(Task task);

    void deleteTask(Long id);
}
