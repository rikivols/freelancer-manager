package fit.biktjv.freelancerManager.entities;

import fit.biktjv.freelancerManager.dataTransferObjects.SkillDTO;
import fit.biktjv.freelancerManager.dataTransferObjects.TaskDTO;
import jakarta.persistence.*;

@Entity
@NamedQuery(name = "allSkills", query = "select skill from Skill skill")
@NamedQuery(name = "skillsForFreelancerId", query =
        "SELECT s FROM Skill s JOIN s.freelancer f WHERE f.id = :freelancerId")
public class Skill {

    @Id
    @GeneratedValue
    @Column(name = "skill_id")
    Long skillId;
    String name;
    @Column(name = "number_of_years")
    Long numberOfYears;
    String notes;

    @ManyToOne(optional = true)
    Freelancer freelancer;

    public Skill(Long skillId, String name, Long numberOfYears, String notes) {
        this.skillId = skillId;
        this.name = name;
        this.numberOfYears = numberOfYears;
        this.notes = notes;
        this.freelancer = null;
    }

    public Skill() {
    }

    public Skill(SkillDTO skillDTO) {
        this.skillId = skillDTO.getSkillId();
        this.name = skillDTO.getName();
        this.numberOfYears = skillDTO.getNumberOfYears();
        this.notes = skillDTO.getNotes();
        this.freelancer = new Freelancer(skillDTO.getFreelancer());
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(Long numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    public SkillDTO toDTO() {
        return new SkillDTO(this);
    }
}
