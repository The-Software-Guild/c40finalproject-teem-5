package tsg.team5.ecommerce.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tsg.team5.ecommerce.entity.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressDaoDBTest {

    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    ExchangeDao exchangeDao;

    @BeforeEach
    void setUp() {
        List<Purchase> purchases = purchaseDao.getAllPurchases();
        for(Purchase purchase : purchases){
            purchaseDao.deletePurchase(purchase.getPurchaseId());
        }

        List<Customer> customers = customerDao.getAllCustomers();
        for(Customer customer : customers){
            customerDao.deleteCustomer(customer.getCustomerId());
        }

        List<Address> addresses = addressDao.getAllAddresses();
        for(Address address : addresses){
            addressDao.deleteAddressById(address.getAddressId());
        }

        List<Item> items = itemDao.getAllItems();
        for(Item item : items){
            itemDao.deleteItem(item.getItemId());
        }

        List<Exchange> exchanges = exchangeDao.getAllExchanges();
        for(Exchange exchange : exchanges){
            exchangeDao.deleteExchangeById(exchange.getExchangeId());
        }
    }

    @Test
    void testAddGetAddress(){
        Address address = new Address();
        address.setStreet("test street");
        address.setCountry("test country");
        address.setPostal("test post"); // Max of 10 char to reflect US and Canada
        address.setCity("test city");
        address.setState("TS"); // Max of 2 char ~ TS: Test State
        address = addressDao.addAddress(address);

        Address fromDao = addressDao.getAddressById(address.getAddressId());
        assertEquals(address, fromDao);
    }

    @Test
    void testGetAddresses(){
        Address address = new Address();
        address.setStreet("test street");
        address.setCountry("test country");
        address.setPostal("test post"); // Max of 10 char to reflect US and Canada
        address.setCity("test city");
        address.setState("TS"); // Max of 2 char ~ TS: Test State
        address = addressDao.addAddress(address);

        Address address2 = new Address();
        address2.setStreet("test street");
        address2.setCountry("test country");
        address2.setPostal("test post"); // Max of 10 char to reflect US and Canada
        address2.setCity("test city");
        address2.setState("TS"); // Max of 2 char ~ TS: Test State
        address2 = addressDao.addAddress(address2);

        List<Address> addresses = addressDao.getAllAddresses();

        assertEquals(2, addresses.size());
        assertTrue(addresses.contains(address));
        assertTrue(addresses.contains(address2));
    }

    @Test
    void testUpdateAddress(){
        Address address = new Address();
        address.setStreet("test street");
        address.setCountry("test country");
        address.setPostal("test post"); // Max of 10 char to reflect US and Canada
        address.setCity("test city");
        address.setState("TS"); // Max of 2 char ~ TS: Test State
        address = addressDao.addAddress(address);

        Address fromDao = addressDao.getAddressById(address.getAddressId());
        assertEquals(address, fromDao);

        address.setStreet("test street 2");
        addressDao.updateAddress(address);
        assertNotEquals(address, fromDao);

        fromDao = addressDao.getAddressById(address.getAddressId());
        assertEquals(address, fromDao);
    }

    @Test
    void testDeleteAddress(){
        Address address = new Address();
        address.setStreet("test street");
        address.setCountry("test country");
        address.setPostal("test post"); // Max of 10 char to reflect US and Canada
        address.setCity("test city");
        address.setState("TS"); // Max of 2 char ~ TS: Test State
        address = addressDao.addAddress(address);
        Address fromDao = addressDao.getAddressById(address.getAddressId());
        assertNotNull(address);

        addressDao.deleteAddressById(address.getAddressId());
        fromDao = addressDao.getAddressById(address.getAddressId());
        assertNull(fromDao);
    }
}