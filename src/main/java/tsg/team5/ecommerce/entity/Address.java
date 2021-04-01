package tsg.team5.ecommerce.entity;

import java.util.Objects;

public class Address {
    int addressId;
    String street;
    String city;
    String state;
    String postal;
    String country;

    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal() {
        return postal;
    }
    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressId == address.addressId && street.equals(address.street) && country.equals(address.country) && postal.equals(address.postal) && city.equals(address.city) && state.equals(address.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, street, country, postal, city, state);
    }
}
