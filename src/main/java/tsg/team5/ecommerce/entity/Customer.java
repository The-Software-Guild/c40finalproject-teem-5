package tsg.team5.ecommerce.entity;

import java.util.Objects;

public class Customer {
    int customerId;
    String customerName;
    String customerEmail;
    String customerPhone;
    Address address;

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId && customerName.equals(customer.customerName) && customerEmail.equals(customer.customerEmail) && customerPhone.equals(customer.customerPhone) && address.equals(customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, customerEmail, customerPhone, address);
    }
}
