package fit.biktjv.freelancerManager.domain;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "allTask", query = "select task from Task task")
@NamedQuery(name = "tasksForFreelancerId", query =
        "SELECT t FROM Task t JOIN t.freelancer f WHERE f.id = :freelancerId")
public class Task {

    @Id
    @GeneratedValue
    Long id;
    String dsc;

    @ManyToOne(optional = false)
    Freelancer freelancer;

    public Task(Long id, String dsc) {
        this.id = id;
        this.dsc = dsc;
    }

    public Task() {
    }

    public Task(TaskDTO taskDTO) {
        dsc = taskDTO.dsc();
    }

    public Task(String dsc, Freelancer freelancer) {
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

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    public TaskDTO toDTO() {
        return new TaskDTO(id, dsc, freelancer.getId());
    }

    public record TaskDTO(Long id, String dsc, Long freelancerId){}
}
