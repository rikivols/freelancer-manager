package fit.biktjv.freelancerManager.entities;
import fit.biktjv.freelancerManager.dataTransferObjects.AddressDTO;
import fit.biktjv.freelancerManager.dataTransferObjects.TaskDTO;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    Long addressId;
    String country;
    String city;
    String street;
    @Column(name = "street_number")
    String streetNumber;
    String zip;

    @OneToMany(mappedBy = "address")
    List<Freelancer> freelancer;

    public Address() {
    }

    public Address(AddressDTO addressDTO) {
        this.addressId = addressDTO.getAddressId();
        this.country = addressDTO.getCountry();
        this.city = addressDTO.getCity();
        this.street = addressDTO.getStreet();
        this.streetNumber = addressDTO.getStreetNumber();
        this.zip = addressDTO.getZip();
    }

    public Address(Long addressId, String country, String city, String street, String streetNumber, String zip) {
        this.addressId = addressId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zip = zip;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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

    public AddressDTO toDTO() {
        return new AddressDTO(this);
    }

    @Override
    public String toString() {
        return "Address:\n" +
                "addressId=" + addressId +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", zip='" + zip + '\'';
    }
}
