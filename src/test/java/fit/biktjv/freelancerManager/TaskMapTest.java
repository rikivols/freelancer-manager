package fit.biktjv.freelancerManager;

import fit.biktjv.freelancerManager.entities.Task;
import fit.biktjv.freelancerManager.repositories.TaskDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active=test")
public class TaskMapTest {

    @Autowired
    TaskDAO taskMapRep;
    Task task;

    @BeforeEach
    public void setUp() {
        taskMapRep.clear();

        task = new Task();
        task.setName("Test task");
        task.setDescription("Description of the task");
        task.setStatus("Need Info");
        task.setPriority("High");
        task.setTimeEstimated("1M");
        task.setReward(500.0F);
        task.setPaid(false);
        task.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testCreateTask() {
        task.setTaskId(0L);
        taskMapRep.createTask(task);
        assertEquals(task, taskMapRep.findTask(2L));
    }

    @Test
    public void testFindTaskSuccess() {
        Task task = taskMapRep.findTask(1L);
        assertEquals(task.getTaskId(), 1L);
        assertEquals(task.getName(), "Test task");
        assertEquals(task.getDescription(), "Description of the task");
        assertEquals(task.getStatus(), "Need Info");
        assertEquals(task.getPriority(), "High");
        assertEquals(task.getTimeEstimated(), "1M");
        assertEquals(task.getReward(), 500.0F);
        assertFalse(task.getPaid());
    }

    @Test
    public void testFindTaskFailure() {
        Task task = taskMapRep.findTask(2L);
        assertNull(task);
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task();
        task.setTaskId(0L);
        taskMapRep.createTask(task);
        task.setName("New task name");
        taskMapRep.updateTask(task);

        // Retrieve the updated task from the repository
        Task updatedTask = taskMapRep.findTask(2L);

        // Assert that the task's name was updated
        assertEquals("New task name", updatedTask.getName());
    }

    @Test
    public void testTasksForFreelancerIdSuccess() {
        // Freelancer has 1 task
        List<Task> tasks = taskMapRep.tasksForFreelancerId(1L);
        assertEquals(1, tasks.size());
        assertEquals(tasks.get(0).getTaskId(), 1L);
        assertEquals(tasks.get(0).getFreelancer().getFreelancerId(), 1L);
    }

    @Test
    public void testTasksForFreelancerIdFailure() {
        // freelancer with id 2 does not exist
        List<Task> tasks = taskMapRep.tasksForFreelancerId(2L);
        assertEquals(0, tasks.size());
    }

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = taskMapRep.getAllTasks();
        assertEquals(tasks.size(), 1);
    }

    @Test
    public void testGetOpenTasks() {
        List<Task> tasks = taskMapRep.getOpenTasks();
        assertEquals(tasks.size(), 1);
    }

    @Test
    public void testGetClosedTasksEmpty() {
        List<Task> tasks = taskMapRep.getClosedTasks();
        assertEquals(tasks.size(), 0);
    }

    @Test
    public void testGetClosedTasksDeclined() {
        task.setStatus("Declined");
        taskMapRep.createTask(task);
        List<Task> tasks = taskMapRep.getClosedTasks();
        assertEquals(tasks.size(), 1);
    }

    @Test
    public void testGetClosedTasksDone() {
        task.setStatus("Done");
        taskMapRep.createTask(task);
        List<Task> tasks = taskMapRep.getClosedTasks();
        assertEquals(tasks.size(), 1);
    }

    @Test
    public void testGetUnassignedTasksEmpty() {
        List<Task> tasks = taskMapRep.getUnassignedTasks();
        assertEquals(tasks.size(), 0);
    }

    @Test
    public void testGetUnassignedTasks() {
        task.setFreelancer(null);
        taskMapRep.createTask(task);
        List<Task> tasks = taskMapRep.getUnassignedTasks();
        assertEquals(tasks.size(), 1);
    }

    @Test
    public void testAssignedTasks() {
        List<Task> tasks = taskMapRep.getAssignedTasks();
        assertEquals(tasks.size(), 1);
    }
}