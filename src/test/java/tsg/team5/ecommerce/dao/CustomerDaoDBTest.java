package tsg.team5.ecommerce.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tsg.team5.ecommerce.entity.Address;
import tsg.team5.ecommerce.entity.Customer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerDaoDBTest {

    @Autowired
    AddressDao addressDao;

    @Autowired
    CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        List<Customer> customers = customerDao.getAllCustomers();
        for(Customer customer : customers){
            customerDao.deleteCustomer(customer.getCustomerId());
        }

        List<Address> addresses = addressDao.getAllAddresses();
        for(Address address : addresses){
            addressDao.deleteAddressById(address.getAddressId());
        }
    }

    @Test
    void getAllCustomers() {
        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setCountry("Country 1");
        address.setState("TX");
        address.setPostal("85490");
        address = addressDao.addAddress(address);

        Address address2 = new Address();
        address2.setStreet("Street 2");
        address2.setCity("City 2");
        address2.setCountry("Country 2");
        address2.setState("CA");
        address2.setPostal("90210");
        address2 = addressDao.addAddress(address2);

        Customer customer = new Customer();
        customer.setCustomerName("Name 1");
        customer.setCustomerEmail("abc@abc.com");
        customer.setCustomerPhone("3879894356");
        customer.setAddress(address);
        customer = customerDao.addCustomer(customer);

        Customer customer2 = new Customer();
        customer2.setCustomerName("Name 2");
        customer2.setCustomerEmail("xyz@abc.com");
        customer2.setCustomerPhone("1112224466");
        customer2.setAddress(address2);
        customer2 = customerDao.addCustomer(customer2);

        List<Customer> customers = customerDao.getAllCustomers();

        assertEquals(2, customers.size());
        assertTrue(customers.contains(customer));
        assertTrue(customers.contains(customer2));
    }

    @Test
    void addAndGetCustomer() {
        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setCountry("Country 1");
        address.setState("TX");
        address.setPostal("85490");
        address = addressDao.addAddress(address);

        Customer customer = new Customer();
        customer.setCustomerName("Name 1");
        customer.setCustomerEmail("abc@abc.com");
        customer.setCustomerPhone("3879894356");
        customer.setAddress(address);
        customer = customerDao.addCustomer(customer);

        Customer fromDao = customerDao.getCustomerById(customer.getCustomerId());
        assertEquals(customer, fromDao);
    }

    @Test
    void updateCustomer() {
        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setCountry("Country 1");
        address.setState("TX");
        address.setPostal("85490");
        address = addressDao.addAddress(address);

        Customer customer = new Customer();
        customer.setCustomerName("Name 1");
        customer.setCustomerEmail("abc@abc.com");
        customer.setCustomerPhone("3879894356");
        customer.setAddress(address);
        customer = customerDao.addCustomer(customer);

        Customer fromDao = customerDao.getCustomerById(customer.getCustomerId());
        assertEquals(customer, fromDao);

        customer.setCustomerName("New Name 1");
        customerDao.updateCustomer(customer);
        assertNotEquals(customer, fromDao);

        fromDao = customerDao.getCustomerById(customer.getCustomerId());
        assertEquals(customer, fromDao);
    }

    @Test
    void deleteCustomer() {
        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setCountry("Country 1");
        address.setState("TX");
        address.setPostal("85490");
        address = addressDao.addAddress(address);

        Customer customer = new Customer();
        customer.setCustomerName("Name 1");
        customer.setCustomerEmail("abc@abc.com");
        customer.setCustomerPhone("3879894356");
        customer.setAddress(address);
        customer = customerDao.addCustomer(customer);

        customerDao.deleteCustomer(customer.getCustomerId());
        Customer fromDao = customerDao.getCustomerById(customer.getCustomerId());
        assertNull(fromDao);
    }
}