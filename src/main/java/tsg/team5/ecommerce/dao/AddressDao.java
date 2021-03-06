package tsg.team5.ecommerce.dao;

import tsg.team5.ecommerce.entity.Address;

import java.util.List;

public interface AddressDao {
    public Address getAddressById(int addressId);
    public List<Address> getAllAddresses();
    public Address addAddress(Address address);
    public void updateAddress(Address address);
    public void deleteAddressById(int addressId);
}
