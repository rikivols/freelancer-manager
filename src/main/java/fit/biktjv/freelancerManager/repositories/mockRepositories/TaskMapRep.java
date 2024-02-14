package fit.biktjv.freelancerManager.repositories.mockRepositories;

import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.TaskDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskMapRep implements TaskDAO {

    private Map<Long, Task> tasks = new HashMap<>();

    public TaskMapRep() {
        init();
    }

    void init() {
        Task task = new Task();
        task.setName("Test task");
        task.setDescription("Description of the task");
        task.setStatus("Need Info");
        task.setPriority("High");
        task.setTimeEstimated("1M");
        task.setReward(500.0F);
        task.setPaid(false);
        task.setCreatedAt(LocalDateTime.now());

        Freelancer freelancer = new Freelancer();
        freelancer.setFreelancerId(1L);
        freelancer.setFirstName("Tom");

        task.setFreelancer(freelancer);

        createTask(task);
    }

    public void clear() {
        // reset the tables after each test
        tasks.clear();
        init();
    }

    @Override
    public Task findTask(Long taskId) {
        return tasks.get(taskId);
    }

    @Override
    public Long createTask(Task task) {
        Long taskId = tasks.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
        task.setTaskId(taskId);
        tasks.put(taskId, task);
        return taskId;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Task> getOpenTasks() {
        List<Task> openTasks = new ArrayList<>();

        for (Task task : tasks.values()) {
            if (task.isOpen()) {
                openTasks.add(task);
            }
        }

        return openTasks;
    }

    @Override
    public List<Task> getClosedTasks() {
        List<Task> closedTasks = new ArrayList<>();

        for (Task task : tasks.values()) {
            if (!task.isOpen()) {
                closedTasks.add(task);
            }
        }

        return closedTasks;
    }

    @Override
    public List<Task> getUnassignedTasks() {
        List<Task> unassignedTasks = new ArrayList<>();

        for (Task task : tasks.values()) {
            if (task.getFreelancer() == null) {
                unassignedTasks.add(task);
            }
        }

        return unassignedTasks;
    }

    @Override
    public List<Task> getAssignedTasks() {
        List<Task> assignedTasks = new ArrayList<>();

        for (Task task : tasks.values()) {
            if (task.getFreelancer() != null) {
                assignedTasks.add(task);
            }
        }

        return assignedTasks;
    }

    public List<Task> tasksForFreelancerId(Long freelancerId) {
        List<Task> tasksForFreelancer = new ArrayList<>();

        for (Task task : tasks.values()) {
            if (task.getFreelancer() != null && task.getFreelancer().getFreelancerId().equals(freelancerId)) {
                tasksForFreelancer.add(task);
            }
        }

        return tasksForFreelancer;
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getTaskId(), task);
    }

    @Override
    public void deleteTask(Long taskId) {
        tasks.remove(taskId);
    }

}
