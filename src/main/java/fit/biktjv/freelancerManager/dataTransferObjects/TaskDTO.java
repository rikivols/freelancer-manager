package fit.biktjv.freelancerManager.dataTransferObjects;

import fit.biktjv.freelancerManager.entities.Task;

import java.time.LocalDateTime;
import java.util.Objects;

public class TaskDTO {

    private Long taskId;
    private String name;
    private String description;
    private String status;
    private String priority;
    private String timeEstimated;

    private Float reward;
    private boolean paid;
    private Long freelancerId;

    private LocalDateTime createdAt;

    public TaskDTO() {
    }

    public TaskDTO(Task task) {
        this.taskId = task.getTaskId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        this.timeEstimated = task.getTimeEstimated();
        this.reward = task.getReward();
        this.paid = task.getPaid();
        this.createdAt = task.getCreatedAt();
        if (task.getFreelancer() != null) {
            this.freelancerId = task.getFreelancer().getFreelancerId();
        }
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

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("""
                        Task DTO:
                        name=%s
                        description=%s
                        status=%s
                        priority=%s
                        reward=%s
                        paid=%s
                        freelancerId=%s""",
                name, description, status, priority, reward, paid, freelancerId);
    }

}
