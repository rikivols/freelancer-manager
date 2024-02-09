package fit.biktjv.freelancerManager.entities;
import fit.biktjv.freelancerManager.dataTransferObjects.TaskDTO;

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

    @ManyToOne(optional = true)
    Freelancer freelancer;

    public Task(Long id, String dsc) {
        this.id = id;
        this.dsc = dsc;
    }

    public Task() {
    }

    public Task(TaskDTO taskDTO) {
        this.id = taskDTO.getId();
        this.dsc = taskDTO.getDsc();
        // You need to retrieve the Freelancer object associated with the freelancerId
        // This is just a placeholder, replace it with your actual logic
//        this.freelancer = new Freelancer(taskDTO.getFreelancerId());
    }

    public Task(String dsc) {
        this.dsc = dsc;
        this.freelancer = null;
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

    public TaskDTO toDTO() {
        if (freelancer == null) {
            return new TaskDTO(id, dsc, null);
        }
        return new TaskDTO(id, dsc, freelancer.toDTO());
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

}
