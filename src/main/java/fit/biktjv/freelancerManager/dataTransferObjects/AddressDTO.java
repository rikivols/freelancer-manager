package fit.biktjv.freelancerManager.dataTransferObjects;

import fit.biktjv.freelancerManager.entities.Address;

public class AddressDTO {
    private Long addressId;
    private String country;
    private String city;

    private String street;
    private String streetNumber;
    private String zip;

    public AddressDTO(Address address) {
        this.addressId = address.getAddressId();
        this.country = address.getCountry();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.streetNumber = address.getStreetNumber();
        this.zip = address.getZip();
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

    @Override
    public String toString() {
        return String.format("Address DTO:\naddressId=%d\ncountry=%s\ncity=%s\nstreet=%s\nstreetNumber=%s\nzip=%s",
                addressId, country, city, street, streetNumber, zip);
    }
}
