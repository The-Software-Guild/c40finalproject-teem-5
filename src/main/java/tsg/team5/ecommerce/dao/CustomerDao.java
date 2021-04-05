package tsg.team5.ecommerce.dao;

import tsg.team5.ecommerce.entity.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    Customer getCustomerById(int id);
    Customer addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);
}
