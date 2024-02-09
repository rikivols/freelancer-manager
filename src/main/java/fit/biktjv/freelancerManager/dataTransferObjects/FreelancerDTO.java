package fit.biktjv.freelancerManager.dataTransferObjects;

import java.util.Objects;

public class FreelancerDTO {
    private Long id;
    private String name;

    public FreelancerDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return String.format("Freelancer DTO:\nid=%d\nname=%s", id, name);
    }
}
