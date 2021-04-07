package tsg.team5.ecommerce.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tsg.team5.ecommerce.entity.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemDaoDBTest {

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
    void getAllItems() {

        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item = itemDao.addItem(item);

        Item item2 = new Item();
        item2.setItemId(2);
        item2.setItemName("example2");
        item2.setCategory("category2");
        item2.setPrice(2.50);
        item2 = itemDao.addItem(item2);

        List<Item> allItems = itemDao.getAllItems();
        assertEquals(2, allItems.size());
    }

    @Test
    void addAndGetItemById() {

        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item = itemDao.addItem(item);

        Item item2 = new Item();
        item2.setItemId(1);
        item2.setItemName("example2");
        item2.setCategory("category2");
        item2.setPrice(2.50);
        item2 = itemDao.addItem(item2);

        Item itemHolder = itemDao.getItemById(item2.getItemId());
        assertNotEquals(itemHolder, item2);
        assertEquals("example1", itemHolder.getItemName());
    }

    @Test
    void updateItem() {

        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item = itemDao.addItem(item);

        item.setItemName("new example 1");
        itemDao.updateItem(item);

        assertEquals("new example 1", item.getItemName());
    }

    @Test
    void deleteItem() {

        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item = itemDao.addItem(item);

        Item holder = itemDao.getItemById(item.getItemId());
        itemDao.deleteItem(item.getItemId());
        holder = itemDao.getItemById(item.getItemId());
        assertNull(holder);
    }
}