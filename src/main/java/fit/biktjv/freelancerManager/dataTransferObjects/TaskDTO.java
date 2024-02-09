package fit.biktjv.freelancerManager.dataTransferObjects;

import java.util.Objects;

public class TaskDTO {
    private Long id;
    private String dsc;
    private FreelancerDTO freelancer;

    public TaskDTO(Long id, String dsc, FreelancerDTO freelancer) {
        this.id = id;
        this.dsc = dsc;
        this.freelancer = freelancer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public FreelancerDTO getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(FreelancerDTO freelancer) {
        this.freelancer = freelancer;
    }

    @Override
    public String toString() {
        return String.format("Task DTO:\nid=%d\ndsc=%s", id, dsc);
    }

}
