package fit.biktjv.freelancerManager.web.forms;

import fit.biktjv.freelancerManager.entities.Skill;

public class SkillForm {
    Long skillId;
    String name;
    int yearsOfExperience;
    String notes;

    public SkillForm() {
    }

    public SkillForm(Skill skill) {
        this.skillId = skill.getSkillId();
        this.name = skill.getName();
        this.yearsOfExperience = skill.getYearsOfExperience();
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

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
