package fit.biktjv.freelancerManager.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@NamedQuery(name = "allFreelancer", query = "select freelancer from Freelancer freelancer")
public class Freelancer {
    @Id
    @GeneratedValue
    Long id;
    String name;
    @OneToMany(mappedBy = "freelancer", orphanRemoval = true)
    List<Task> tasks;

    public Freelancer() {
    }

    public Freelancer(FreelancerDTO freelancerDTO) {
        name = freelancerDTO.name;
    }

    public Freelancer(String name) {
        this.name =name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FreelancerDTO toDTO() {
        return new FreelancerDTO(id, name);
    }

    public record FreelancerDTO(Long id, String name) {
    }

    @Override
    public String toString() {
        return String.format("%d:%s" ,id ,name);
    }
}
