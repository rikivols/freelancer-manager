package fit.biktjv.freelancerManager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fit.biktjv.freelancerManager.dataTransferObjects.SkillDTO;
import fit.biktjv.freelancerManager.dataTransferObjects.TaskDTO;
import fit.biktjv.freelancerManager.web.forms.SkillForm;
import jakarta.persistence.*;

@Entity
@NamedQuery(name = "allSkills", query = "select skill from Skill skill")
@NamedQuery(name = "skillsForFreelancerId", query =
        "SELECT s FROM Skill s JOIN s.freelancer f WHERE f.id = :freelancerId")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    Long skillId;
    String name;
    @Column(name = "years_of_experience")
    Integer yearsOfExperience;

    @Column(columnDefinition = "TEXT")
    String notes;

    @ManyToOne(optional = true)
    @JsonBackReference
    Freelancer freelancer;

    public Skill(Long skillId, String name, Integer yearsOfExperience, String notes) {
        this.skillId = skillId;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.notes = notes;
        this.freelancer = null;
    }

    public Skill() {
    }

    public Skill(SkillDTO skillDTO) {
        this.name = skillDTO.getName();
        this.yearsOfExperience = skillDTO.getYearsOfExperience();
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

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
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

    public void updateFromForm(SkillForm skillForm) {
        this.name = skillForm.getName();
        this.yearsOfExperience = skillForm.getYearsOfExperience();
        this.notes = skillForm.getNotes();
    }

    static public SkillForm toForm(Skill skill) {
        return new SkillForm(skill);
    }

    public SkillDTO toDTO() {
        return new SkillDTO(this);
    }
}
