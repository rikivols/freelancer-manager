package fit.biktjv.freelancerManager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fit.biktjv.freelancerManager.dataTransferObjects.FreelancerDTO;
import fit.biktjv.freelancerManager.dataTransferObjects.SkillDTO;
import fit.biktjv.freelancerManager.web.forms.FreelancerForm;
import fit.biktjv.freelancerManager.web.forms.SkillForm;
import fit.biktjv.freelancerManager.web.forms.TaskForm;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "allFreelancersQuery", query = "select freelancer from Freelancer freelancer")
public class Freelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "freelancer_id")
    Long freelancerId;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "middle_name")
    String middleName;
    @Column(name = "last_name")
    String lastName;
    String email;
    @Column(name = "phone_number")
    String phoneNumber;
    LocalDate birthday;
    @Column(name = "additional_information", columnDefinition = "TEXT")
    String additionalInformation;

    @OneToMany(mappedBy = "freelancer")
    @JsonBackReference
    List<Task> tasks;
    @ManyToOne()
    @JsonBackReference
    Address address;
    @OneToMany(mappedBy = "freelancer")
    @JsonBackReference
    List<Skill> skills;

    public Freelancer(String firstName, String middleName, String lastName, String email,
                      String phoneNumber, LocalDate birthday, String additionalInformation) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.additionalInformation = additionalInformation;
    }

    public Freelancer(FreelancerForm freelancerForm) {
        this.firstName = freelancerForm.getFirstName();
        this.middleName = freelancerForm.getMiddleName();
        this.lastName = freelancerForm.getLastName();
        this.email = freelancerForm.getEmail();
        this.phoneNumber = freelancerForm.getPhoneNumber();
        this.birthday = freelancerForm.getBirthday();
        this.additionalInformation = freelancerForm.getAdditionalInformation();

        // add address to freelancer when creating new freelancer
        Address address = new Address();
        address.setCountry(freelancerForm.getCountry());
        address.setCity(freelancerForm.getCity());
        address.setStreet(freelancerForm.getStreet());
        address.setStreetNumber(freelancerForm.getStreetNumber());
        address.setZip(freelancerForm.getZip());

        this.address = address;

        // add skills to freelancer when creating new freelancer
        List<Skill> skills = new ArrayList<>();
        if (freelancerForm.getSkills() != null) {
            for (SkillForm skillForm : freelancerForm.getSkills()) {
                Skill skill = new Skill();
                skill.setName(skillForm.getName());
                skill.setYearsOfExperience(skillForm.getYearsOfExperience());
                skill.setNotes(skillForm.getNotes());
                skill.setFreelancer(this);
                skills.add(skill);
            }
        }

        this.skills = skills;
    }

    public Freelancer(FreelancerDTO freelancerDTO) {
        this.firstName = freelancerDTO.getFirstName();
        this.middleName = freelancerDTO.getMiddleName();
        this.lastName = freelancerDTO.getLastName();
        this.email = freelancerDTO.getEmail();
        this.phoneNumber = freelancerDTO.getPhoneNumber();
        this.birthday = freelancerDTO.getBirthday();
        this.additionalInformation = freelancerDTO.getAdditionalInformation();
        this.address = freelancerDTO.getAddress().toEntity();
        this.skills = freelancerDTO.getSkills().stream().map(SkillDTO::toEntity).toList();
    }

    public Freelancer() {

    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public FreelancerDTO toDTO() {
        return new FreelancerDTO(this);
    }

    @Override
    public String toString() {
        if (middleName == null || middleName.isEmpty()) {
            return firstName + " " + lastName;
        } else {
            return firstName + " " + middleName + " " + lastName;
        }
    }
}
