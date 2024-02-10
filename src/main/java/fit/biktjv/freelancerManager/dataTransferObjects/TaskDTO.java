package fit.biktjv.freelancerManager.dataTransferObjects;

import fit.biktjv.freelancerManager.entities.Task;
import java.util.Objects;

public class TaskDTO {
    private Long taskId;
    private String name;
    private String description;
    private String status;
    private Float reward;
    private boolean paid;
    private FreelancerDTO freelancer;

    public TaskDTO() {
    }

    public TaskDTO(Long taskId, String name, String description, String status, Float reward,
                   boolean paid, FreelancerDTO freelancer) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.reward = reward;
        this.paid = paid;
        this.freelancer = freelancer;
    }

    public TaskDTO(Task task) {
        this.taskId = task.getTaskId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.reward = task.getReward();
        this.paid = task.isPaid();
        if (task.getFreelancer() != null) {
            this.freelancer = task.getFreelancer().toDTO();
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

    public FreelancerDTO getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(FreelancerDTO freelancer) {
        this.freelancer = freelancer;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", reward=" + reward +
                ", paid=" + paid +
                ", freelancer=" + freelancer +
                '}';
    }

}
