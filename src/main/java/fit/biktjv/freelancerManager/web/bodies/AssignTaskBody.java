package fit.biktjv.freelancerManager.web.bodies;

public class AssignTaskBody {
    private Long taskId;
    private Long freelancerId;

    // getters and setters
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }
}
