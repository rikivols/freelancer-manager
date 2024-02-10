package fit.biktjv.freelancerManager.dataTransferObjects;

import fit.biktjv.freelancerManager.entities.Skill;

public class SkillDTO {
    private Long skillId;
    private String name;
    private Long numberOfYears;
    private String notes;
    private FreelancerDTO freelancer;

    public SkillDTO() {
    }

    public SkillDTO(Skill skill) {
        this.skillId = skill.getSkillId();
        this.name = skill.getName();
        this.numberOfYears = skill.getNumberOfYears();
        this.notes = skill.getNotes();
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

    public FreelancerDTO getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(FreelancerDTO freelancer) {
        this.freelancer = freelancer;
    }

    @Override
    public String toString() {
        return String.format("Skill DTO:\nskillId=%d\nname=%s\nnumberOfYears=%d\nnotes=%s",
                skillId, name, numberOfYears, notes);
    }
}
