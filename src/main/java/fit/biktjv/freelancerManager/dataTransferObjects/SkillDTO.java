package fit.biktjv.freelancerManager.dataTransferObjects;

import fit.biktjv.freelancerManager.entities.Skill;

public class SkillDTO {
    private String name;
    private Integer yearsOfExperience;
    private String notes;
    private FreelancerDTO freelancer;

    public SkillDTO() {
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

    public FreelancerDTO getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(FreelancerDTO freelancer) {
        this.freelancer = freelancer;
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
