package fit.biktjv.freelancerManager.dataTransferObjects;

import fit.biktjv.freelancerManager.entities.Skill;

public class SkillDTO {
    private String name;
    private Integer yearsOfExperience;
    private String notes;
    private Long freelancerId;

    public SkillDTO() {
    }

    public SkillDTO(String name, Integer yearsOfExperience, String notes, Long freelancerId) {
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.notes = notes;
        this.freelancerId = freelancerId;
    }

    public SkillDTO(Skill skill) {
        this.name = skill.getName();
        this.yearsOfExperience = skill.getYearsOfExperience();
        this.notes = skill.getNotes();
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

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public Skill toEntity() {
        Skill skill = new Skill();
        skill.setName(name);
        skill.setYearsOfExperience(yearsOfExperience);
        skill.setNotes(notes);
        return skill;
    }

    @Override
    public String toString() {
        return String.format("Skill DTO:\nname=%s\nyearsOfExperience=%d\nnotes=%s",
                name, yearsOfExperience, notes);
    }
}
