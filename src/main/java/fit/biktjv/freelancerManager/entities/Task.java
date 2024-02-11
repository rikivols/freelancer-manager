package fit.biktjv.freelancerManager.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fit.biktjv.freelancerManager.dataTransferObjects.TaskDTO;
import fit.biktjv.freelancerManager.web.forms.TaskForm;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "allTasksQuery", query = "select task from Task task")
@NamedQuery(name = "tasksForFreelancerIdQuery", query =
        "SELECT task FROM Task task JOIN task.freelancer f WHERE f.freelancerId = :freelancerId")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    Long taskId;
    String name;

    @Column(columnDefinition = "TEXT")
    String description;
    String status;
    String priority;
    String timeEstimated;
    Float reward;
    boolean paid;

    @ManyToOne(optional = true)
    @JsonManagedReference
    Freelancer freelancer;

    public Task() {
    }

    public Task(TaskForm taskForm) {
        this.name = taskForm.getName();
        this.description = taskForm.getDescription();
        this.status = taskForm.getStatus();
        this.priority = taskForm.getPriority();
        this.timeEstimated = taskForm.getTimeEstimated();
        this.reward = taskForm.getReward();
        this.paid = taskForm.isPaid();
    }

    public Task(TaskDTO taskDTO) {
        this.name = taskDTO.getName();
        this.description = taskDTO.getDescription();
        this.status = taskDTO.getStatus();
        this.priority = taskDTO.getPriority();
        this.timeEstimated = taskDTO.getTimeEstimated();
        this.reward = taskDTO.getReward();
        this.paid = taskDTO.isPaid();
        this.freelancer = null;
    }

    public Task(Long taskId, String description, String status, String priority,
                String timeEstimated, Float reward, boolean paid) {
        this.taskId = taskId;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.timeEstimated = timeEstimated;
        this.reward = reward;
        this.paid = paid;
        this.freelancer = null;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTimeEstimated() {
        return timeEstimated;
    }

    public void setTimeEstimated(String timeEstimated) {
        this.timeEstimated = timeEstimated;
    }

    public Float getReward() {
        return reward;
    }

    public void setReward(Float reward) {
        this.reward = reward;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    public boolean isOpen() {
        return !status.equals("Done") && !status.equals("Declined");
    }

    public TaskDTO toDTO() {
        return new TaskDTO(this);
    }

    public void updateFromForm(TaskForm taskForm) {
        this.name = taskForm.getName();
        this.description = taskForm.getDescription();
        this.status = taskForm.getStatus();
        this.priority = taskForm.getPriority();
        this.timeEstimated = taskForm.getTimeEstimated();
        this.reward = taskForm.getReward();
        this.paid = taskForm.isPaid();
    }

}
