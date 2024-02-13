package fit.biktjv.freelancerManager.repositories;

import fit.biktjv.freelancerManager.entities.Task;

import java.util.List;

public interface TaskDAO {

    Task findTask(Long taskId);
    Long createTask(Task task);

    List<Task> getAllTasks();

    List<Task> getOpenTasks();

    List<Task> getClosedTasks();

    List<Task> getUnassignedTasks();

    List<Task> getAssignedTasks();


    List<Task> tasksForFreelancerId(Long freelancerId);

    void updateTask(Task task);

    void clear();

    void deleteTask(Long id);
}
