package fit.biktjv.freelancerManager.dataTransferObjects;

import fit.biktjv.freelancerManager.entities.Address;
import fit.biktjv.freelancerManager.dataTransferObjects.AddressDTO;
import fit.biktjv.freelancerManager.dataTransferObjects.SkillDTO;
import fit.biktjv.freelancerManager.entities.Freelancer;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FreelancerDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;
    private String additionalInformation;

    private AddressDTO address;
    private List<SkillDTO> skills;


    public FreelancerDTO() {
    }

    public FreelancerDTO(Freelancer freelancer) {
        this.firstName = freelancer.getFirstName();
        this.middleName = freelancer.getMiddleName();
        this.lastName = freelancer.getLastName();
        this.email = freelancer.getEmail();
        this.phoneNumber = freelancer.getPhoneNumber();
        this.birthday = freelancer.getBirthday();
        this.additionalInformation = freelancer.getAdditionalInformation();
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<SkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDTO> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String skillz = mapper.writeValueAsString(skills);

            return String.format("""
                            Freelancer DTO:
                            firstName=%s
                            middleName=%s
                            lastName=%s
                            email=%s
                            phoneNumber=%s
                            birthday=%s
                            additionalInformation=%s
                            address=%s
                            skills=%s
                            """,
                    firstName, middleName, lastName, email, phoneNumber, birthday,
                    additionalInformation, address, skillz);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
