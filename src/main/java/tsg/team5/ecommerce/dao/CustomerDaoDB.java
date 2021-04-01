package tsg.team5.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tsg.team5.ecommerce.entity.Address;
import tsg.team5.ecommerce.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDaoDB implements CustomerDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Customer> getAllCustomers() {
        final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer";
        List<Customer> customers = jdbc.query(SELECT_ALL_CUSTOMERS, new CustomerMapper());
        associateWithAddress(customers);
        return customers;
    }

    // Method used to associate address with the customer in regards to the foreign key
    private void associateWithAddress(List<Customer> customers) {
        for(Customer customer : customers){
            customer.setAddress(getAddressForCustomer(customer.getCustomerId()));
        }
    }

    // Gets the address object for the customer instead of having the address id
    private Address getAddressForCustomer(int id) {
        final String SELECT_ADDRESS_FOR_CUSTOMER = "SELECT a.* FROM address a JOIN customer c ON c.addressId = a.addressId WHERE c.customerId = ?";
        return jdbc.queryForObject(SELECT_ADDRESS_FOR_CUSTOMER, new AddressDaoDB.AddressMapper(), id);
    }

    @Override
    public Customer getCustomerById(int id) {
        try{
            final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM power WHERE id = ?";
            Customer customer = jdbc.queryForObject(SELECT_CUSTOMER_BY_ID, new CustomerMapper(), id);
            customer.setAddress(getAddressForCustomer(id));
            return customer;

        }catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    @Transactional
    public Customer addCustomer(Customer customer) {
        final String INSERT_CUSTOMER = "INSERT INTO customer(name, email, phone) VALUES (?,?,?)";
        jdbc.update(INSERT_CUSTOMER, customer.getCustomerName(), customer.getCustomerEmail(), customer.getCustomerPhone());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        customer.setCustomerId(newId);

        return customer;
    }

    @Override
    public void updateCustomer(Customer customer) {
        final String UPDATE_SUPER = "UPDATE customer SET customerName = ?, customerEmail = ?, customerPhone = ? WHERE customerId = ?";
        jdbc.update(UPDATE_SUPER, customer.getCustomerName(), customer.getCustomerEmail(), customer.getCustomerPhone(),
                customer.getCustomerId());
    }

    @Override
    @Transactional
    public void deleteCustomer(int id) {
        final String DELETE_PURCHASE_CUSTOMER = "DELETE FROM purchase WHERE customerId = ?";
        jdbc.update(DELETE_PURCHASE_CUSTOMER, id);

        final String DELETE_CUSTOMER = "DELETE FROM customer WHERE customerId = ?";
        jdbc.update(DELETE_CUSTOMER, id);
    }

    public static final class CustomerMapper implements RowMapper<Customer>{

        @Override
        public Customer mapRow(ResultSet rs, int i) throws SQLException {
            Customer customer = new Customer();
            customer.setCustomerId(rs.getInt("customerId"));
            customer.setCustomerName(rs.getString("customerName"));
            customer.setCustomerEmail(rs.getString("customerEmail"));
            customer.setCustomerPhone(rs.getString("customerPhone"));

            return customer;
        }
    }
}
