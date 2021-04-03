package tsg.team5.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tsg.team5.ecommerce.entity.Address;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AddressDaoDB implements AddressDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Address getAddressById(int addressId) {
        try {
            final String GET_ADDRESS_BY_ID = "SELECT * FROM Address WHERE addressId = ?;";
            return jdbc.queryForObject(GET_ADDRESS_BY_ID, new AddressMapper(), addressId);
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Address> getAllAddresses() {
        final String GET_ALL_ADDRESSES = "SELECT * FROM Address;";
        return jdbc.query(GET_ALL_ADDRESSES, new AddressMapper());
    }

    @Override
    public Address addAddress(Address address){
        final String ADD_ADDRESS = "INSERT INTO Address " +
                "(street, country, postal, city, state) VALUES " +
                "(?, ?, ?, ?, ?);";
        jdbc.update(ADD_ADDRESS,
                address.getStreet(),
                address.getCountry(),
                address.getPostal(),
                address.getCity(),
                address.getState());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        address.setAddressId(newId);

        return address;
    }

    @Override
    public void updateAddress(Address address) {
        final String UPDATE_ADDRESS =
                "UPDATE Address SET " +
                "street = ?, " +
                "country = ?, " +
                "postal = ?, " +
                "city = ?, " +
                "state = ? " +
                "WHERE addressId = ?;";
        jdbc.update(UPDATE_ADDRESS,
                address.getStreet(),
                address.getCountry(),
                address.getPostal(),
                address.getCity(),
                address.getState(),
                address.getAddressId());
    }

    @Override
    public void deleteAddressById(int addressId) {
        final String DELETE_ADDRESS_BY_ID =
                "DELETE FROM Address WHERE addressId = ?;";
        jdbc.update(DELETE_ADDRESS_BY_ID, addressId);
    }

    public static final class AddressMapper implements RowMapper<Address> {
        @Override
        public Address mapRow(ResultSet rs, int index) throws SQLException {
            Address address = new Address();
            address.setAddressId(rs.getInt("addressId"));
            address.setStreet(rs.getString("street"));
            address.setCountry(rs.getString("country"));
            address.setPostal(rs.getString("postal"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            return address;
        }
    }
}
