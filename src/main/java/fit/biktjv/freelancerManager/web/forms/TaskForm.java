package fit.biktjv.freelancerManager.web.forms;

import fit.biktjv.freelancerManager.entities.Task;

import java.time.LocalDateTime;

public class TaskForm {
    Long freelancerId = null;
    String name;
    String description;
    String status;
    String priority;
    String timeEstimated;
    Float reward;
    boolean paid;

    public TaskForm() {
    }

    public TaskForm(Task task) {
        if (task.getFreelancer() != null) {
            this.freelancerId = task.getFreelancer().getFreelancerId();
        }
        this.name = task.getName();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        this.timeEstimated = task.getTimeEstimated();
        this.reward = task.getReward();
        this.paid = task.getPaid();
    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
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

}
