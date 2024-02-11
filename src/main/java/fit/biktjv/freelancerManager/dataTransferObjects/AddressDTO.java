package fit.biktjv.freelancerManager.dataTransferObjects;

import fit.biktjv.freelancerManager.entities.Address;

public class AddressDTO {
    private String country;
    private String city;

    private String street;
    private String streetNumber;
    private String zip;

    public AddressDTO() {
    }

    public AddressDTO(Address address) {
        this.country = address.getCountry();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.streetNumber = address.getStreetNumber();
        this.zip = address.getZip();
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
        return street;
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

    public Address toEntity() {
        Address address = new Address();
        address.setCountry(this.country);
        address.setCity(this.city);
        address.setStreet(this.street);
        address.setStreetNumber(this.streetNumber);
        address.setZip(this.zip);
        return address;
    }

    @Override
    public String toString() {
        return String.format("Address DTO:\ncountry=%s\ncity=%s\nstreet=%s\nstreetNumber=%s\nzip=%s",
                             country, city, street, streetNumber, zip);
    }
}
