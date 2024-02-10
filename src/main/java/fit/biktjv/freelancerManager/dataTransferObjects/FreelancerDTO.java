package fit.biktjv.freelancerManager.dataTransferObjects;

import fit.biktjv.freelancerManager.entities.Freelancer;

import java.time.LocalDate;
import java.util.Objects;

public class FreelancerDTO {
    private Long freelancerId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;
    private String additionalInformation;

    public FreelancerDTO() {
    }

    public FreelancerDTO(Freelancer freelancer) {
        this.freelancerId = freelancer.getFreelancerId();
        this.firstName = freelancer.getFirstName();
        this.middleName = freelancer.getMiddleName();
        this.lastName = freelancer.getLastName();
        this.email = freelancer.getEmail();
        this.phoneNumber = freelancer.getPhoneNumber();
        this.birthday = freelancer.getBirthday();
        this.additionalInformation = freelancer.getAdditionalInformation();
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

    @Override
    public String toString() {
        return String.format("""
                        Freelancer DTO:
                        freelancerId=%d
                        firstName=%s
                        middleName=%s
                        lastName=%s
                        email=%s
                        phoneNumber=%s
                        birthday=%s
                        additionalInformation=%s""",
                freelancerId, firstName, middleName, lastName, email, phoneNumber, birthday, additionalInformation);
    }
}
