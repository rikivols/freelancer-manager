package fit.biktjv.freelancerManager.repositories;

import fit.biktjv.freelancerManager.entities.Task;

import java.util.List;

public interface TaskDAO {

    Long createTask(Task task);
    List<Task> getAllTasks();
}
