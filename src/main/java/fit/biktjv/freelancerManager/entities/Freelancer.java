package fit.biktjv.freelancerManager.entities;

import fit.biktjv.freelancerManager.dataTransferObjects.FreelancerDTO;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NamedQuery(name = "allFreelancersQuery", query = "select freelancer from Freelancer freelancer")
public class Freelancer {
    @Id
    @GeneratedValue
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
    @Column(name = "additional_information")
    String additionalInformation;

    @OneToMany(mappedBy = "freelancer")
    List<Task> tasks;
    @ManyToOne()
    Address address;
    @OneToMany(mappedBy = "freelancer")
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

    public Freelancer(FreelancerDTO freelancerDTO) {
        this.freelancerId = freelancerDTO.getFreelancerId();
        this.firstName = freelancerDTO.getFirstName();
        this.middleName = freelancerDTO.getMiddleName();
        this.lastName = freelancerDTO.getLastName();
        this.email = freelancerDTO.getEmail();
        this.phoneNumber = freelancerDTO.getPhoneNumber();
        this.birthday = freelancerDTO.getBirthday();
        this.additionalInformation = freelancerDTO.getAdditionalInformation();
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
        return String.format("Freelancer:\nfreelancerId=%d\nfirstName=%s\nmiddleName=%s\nlastName=%s\nemail=%s\nphoneNumber=%s\nbirthday=%s",
                freelancerId, firstName, middleName, lastName, email, phoneNumber, birthday);
    }
}
