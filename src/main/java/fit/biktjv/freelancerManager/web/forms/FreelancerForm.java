package fit.biktjv.freelancerManager.web.forms;

import fit.biktjv.freelancerManager.entities.Address;
import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Skill;
import fit.biktjv.freelancerManager.entities.Task;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class FreelancerForm {
    String firstName;
    String middleName;
    String lastName;
    String email;
    String phoneNumber;
    LocalDate birthday;
    String additionalInformation;
    String country;
    String city;
    String street;
    String streetNumber;
    String zip;

    List<SkillForm> skillForms;

    public FreelancerForm() {
    }

    public FreelancerForm(Freelancer freelancer) {
        this.firstName = freelancer.getFirstName();
        this.middleName = freelancer.getMiddleName();
        this.lastName = freelancer.getLastName();
        this.email = freelancer.getEmail();
        this.phoneNumber = freelancer.getPhoneNumber();
        this.birthday = freelancer.getBirthday();
        this.additionalInformation = freelancer.getAdditionalInformation();

        Address address = freelancer.getAddress();
        this.country = address.getCountry();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.streetNumber = address.getStreetNumber();
        this.zip = address.getZip();

        this.skillForms = freelancer.getSkills().stream().map(Skill::toForm).toList();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<SkillForm> getSkillForms() {
        return skillForms;
    }

    public void setSkillForms(List<SkillForm> skillForms) {
        this.skillForms = skillForms;
    }
}
