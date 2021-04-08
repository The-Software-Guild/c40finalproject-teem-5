package tsg.team5.ecommerce.dao;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tsg.team5.ecommerce.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PurchaseDaoDBTest {

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
    void addAndGetPurchaseById() {
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

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

        List<Integer> quantities = new ArrayList<>();
        quantities.add(5);
        quantities.add(10);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setCurrency("CAD");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);
        purchase.setQuantities(quantities);
        purchase = purchaseDao.addPurchase(purchase);

        Purchase fromDao = purchaseDao.getPurchaseById(purchase.getPurchaseId());
        assertEquals(fromDao, purchase);
    }

    @Test
    void getAllPurchases() {
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

        Exchange exchange2 = new Exchange();
        exchange2.setCny(new BigDecimal("1.1234"));
        exchange2.setCad(new BigDecimal("1.1234"));
        exchange2.setEur(new BigDecimal("1.1234"));
        exchange2.setGbp(new BigDecimal("1.1234"));
        exchange2.setJpy(new BigDecimal("1.1234"));
        exchange2 = exchangeDao.addExchange(exchange2);

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

        List<Integer> quantities = new ArrayList<>();
        quantities.add(5);
        quantities.add(10);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setCurrency("CAD");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);
        purchase.setQuantities(quantities);
        purchase = purchaseDao.addPurchase(purchase);

        Purchase purchase2 = new Purchase();
        purchase2.setPurchaseDate(LocalDate.now());
        purchase2.setCurrency("EUR");
        purchase2.setExchange(exchange2);
        purchase2.setCustomer(customer2);
        purchase2.setItems(items);
        purchase2.setQuantities(quantities);
        purchase2 = purchaseDao.addPurchase(purchase2);

        List<Purchase> purchases = purchaseDao.getAllPurchases();

        assertEquals(2, purchases.size());
        assertTrue(purchases.contains(purchase));
        assertTrue(purchases.contains(purchase2));
    }

    @Test
    void updatePurchase() {
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

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

        List<Integer> quantities = new ArrayList<>();
        quantities.add(5);
        quantities.add(10);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setCurrency("CAD");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);
        purchase.setQuantities(quantities);
        purchase = purchaseDao.addPurchase(purchase);

        Purchase fromDao = purchaseDao.getPurchaseById(purchase.getPurchaseId());
        assertEquals(purchase, fromDao);

        purchase.setCurrency("JPY");
        purchaseDao.updatePurchase(purchase);
        assertNotEquals(purchase, fromDao);

        fromDao = purchaseDao.getPurchaseById(purchase.getPurchaseId());
        assertEquals(purchase, fromDao);
    }

    @Test
    void deletePurchase() {
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

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

        List<Integer> quantities = new ArrayList<>();
        quantities.add(5);
        quantities.add(10);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setCurrency("CAD");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);
        purchase.setQuantities(quantities);
        purchase = purchaseDao.addPurchase(purchase);

        purchaseDao.deletePurchase(purchase.getPurchaseId());
        Purchase fromDao = purchaseDao.getPurchaseById(purchase.getPurchaseId());
        assertNull(fromDao);
    }

    @Test
    void getPurchasesRangeDate() {
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

        Exchange exchange2 = new Exchange();
        exchange2.setCny(new BigDecimal("1.2234"));
        exchange2.setCad(new BigDecimal("1.2234"));
        exchange2.setEur(new BigDecimal("1.2234"));
        exchange2.setGbp(new BigDecimal("1.2234"));
        exchange2.setJpy(new BigDecimal("1.2234"));
        exchange2 = exchangeDao.addExchange(exchange2);

        Exchange exchange3 = new Exchange();
        exchange3.setCny(new BigDecimal("1.3234"));
        exchange3.setCad(new BigDecimal("1.3234"));
        exchange3.setEur(new BigDecimal("1.3234"));
        exchange3.setGbp(new BigDecimal("1.3234"));
        exchange3.setJpy(new BigDecimal("1.3234"));
        exchange3 = exchangeDao.addExchange(exchange3);

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

        List<Integer> quantities = new ArrayList<>();
        quantities.add(5);
        quantities.add(10);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.parse("2021-03-08"));
        purchase.setCurrency("CAD");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);
        purchase.setQuantities(quantities);
        purchase = purchaseDao.addPurchase(purchase);

        Purchase purchase2 = new Purchase();
        purchase2.setPurchaseDate(LocalDate.parse("2021-03-21"));
        purchase2.setCurrency("EUR");
        purchase2.setExchange(exchange2);
        purchase2.setCustomer(customer2);
        purchase2.setItems(items);
        purchase2.setQuantities(quantities);
        purchase2 = purchaseDao.addPurchase(purchase2);

        Purchase purchase3 = new Purchase();
        purchase3.setPurchaseDate(LocalDate.parse("2021-04-01"));
        purchase3.setCurrency("EUR");
        purchase3.setExchange(exchange3);
        purchase3.setCustomer(customer2);
        purchase3.setItems(items);
        purchase3.setQuantities(quantities);
        purchase3 = purchaseDao.addPurchase(purchase3);

        LocalDate from = LocalDate.parse("2021-03-20");
        List<Purchase> fromDao = purchaseDao.getPurchasesRangeDate(from, LocalDate.now());
        assertEquals(2, fromDao.size());
        assertFalse(fromDao.contains(purchase));
        assertTrue(fromDao.contains(purchase2));
        assertTrue(fromDao.contains(purchase3));
    }

    @Test
    void getPurchasesForCustomer() {
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

        Exchange exchange2 = new Exchange();
        exchange2.setCny(new BigDecimal("1.2234"));
        exchange2.setCad(new BigDecimal("1.2234"));
        exchange2.setEur(new BigDecimal("1.2234"));
        exchange2.setGbp(new BigDecimal("1.2234"));
        exchange2.setJpy(new BigDecimal("1.2234"));
        exchange2 = exchangeDao.addExchange(exchange2);

        Exchange exchange3 = new Exchange();
        exchange3.setCny(new BigDecimal("1.3234"));
        exchange3.setCad(new BigDecimal("1.3234"));
        exchange3.setEur(new BigDecimal("1.3234"));
        exchange3.setGbp(new BigDecimal("1.3234"));
        exchange3.setJpy(new BigDecimal("1.3234"));
        exchange3 = exchangeDao.addExchange(exchange3);

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

        List<Integer> quantities = new ArrayList<>();
        quantities.add(5);
        quantities.add(10);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.parse("2021-03-08"));
        purchase.setCurrency("CAD");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);
        purchase.setQuantities(quantities);
        purchase = purchaseDao.addPurchase(purchase);

        Purchase purchase2 = new Purchase();
        purchase2.setPurchaseDate(LocalDate.parse("2021-03-21"));
        purchase2.setCurrency("EUR");
        purchase2.setExchange(exchange2);
        purchase2.setCustomer(customer2);
        purchase2.setItems(items);
        purchase2.setQuantities(quantities);
        purchase2 = purchaseDao.addPurchase(purchase2);

        Purchase purchase3 = new Purchase();
        purchase3.setPurchaseDate(LocalDate.parse("2021-04-01"));
        purchase3.setCurrency("EUR");
        purchase3.setExchange(exchange3);
        purchase3.setCustomer(customer2);
        purchase3.setItems(items);
        purchase3.setQuantities(quantities);
        purchase3 = purchaseDao.addPurchase(purchase3);

        List<Purchase> fromDao = purchaseDao.getPurchasesForCustomer(customer2.getCustomerId());
        assertEquals(2, fromDao.size());
        assertFalse(fromDao.contains(purchase));
        assertTrue(fromDao.contains(purchase2));
        assertTrue(fromDao.contains(purchase3));
    }

    @Test
    void getPurchasesByDate() {
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

        Exchange exchange2 = new Exchange();
        exchange2.setCny(new BigDecimal("1.2234"));
        exchange2.setCad(new BigDecimal("1.2234"));
        exchange2.setEur(new BigDecimal("1.2234"));
        exchange2.setGbp(new BigDecimal("1.2234"));
        exchange2.setJpy(new BigDecimal("1.2234"));
        exchange2 = exchangeDao.addExchange(exchange2);

        Exchange exchange3 = new Exchange();
        exchange3.setCny(new BigDecimal("1.3234"));
        exchange3.setCad(new BigDecimal("1.3234"));
        exchange3.setEur(new BigDecimal("1.3234"));
        exchange3.setGbp(new BigDecimal("1.3234"));
        exchange3.setJpy(new BigDecimal("1.3234"));
        exchange3 = exchangeDao.addExchange(exchange3);

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

        List<Integer> quantities = new ArrayList<>();
        quantities.add(5);
        quantities.add(10);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.parse("2021-03-08"));
        purchase.setCurrency("CAD");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);
        purchase.setQuantities(quantities);
        purchase = purchaseDao.addPurchase(purchase);

        Purchase purchase2 = new Purchase();
        purchase2.setPurchaseDate(LocalDate.parse("2021-03-21"));
        purchase2.setCurrency("EUR");
        purchase2.setExchange(exchange2);
        purchase2.setCustomer(customer2);
        purchase2.setItems(items);
        purchase2.setQuantities(quantities);
        purchase2 = purchaseDao.addPurchase(purchase2);

        Purchase purchase3 = new Purchase();
        purchase3.setPurchaseDate(LocalDate.parse("2021-04-01"));
        purchase3.setCurrency("EUR");
        purchase3.setExchange(exchange3);
        purchase3.setCustomer(customer2);
        purchase3.setItems(items);
        purchase3.setQuantities(quantities);
        purchase3 = purchaseDao.addPurchase(purchase3);

        LocalDate date = LocalDate.parse("2021-03-21");
        List<Purchase> fromDao = purchaseDao.getPurchasesByDate(date);
        assertEquals(1, fromDao.size());
        assertFalse(fromDao.contains(purchase));
        assertTrue(fromDao.contains(purchase2));
        assertFalse(fromDao.contains(purchase3));
    }

    @Test
    void getPurchasesByCurrency() {
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

        Exchange exchange2 = new Exchange();
        exchange2.setCny(new BigDecimal("1.2234"));
        exchange2.setCad(new BigDecimal("1.2234"));
        exchange2.setEur(new BigDecimal("1.2234"));
        exchange2.setGbp(new BigDecimal("1.2234"));
        exchange2.setJpy(new BigDecimal("1.2234"));
        exchange2 = exchangeDao.addExchange(exchange2);

        Exchange exchange3 = new Exchange();
        exchange3.setCny(new BigDecimal("1.3234"));
        exchange3.setCad(new BigDecimal("1.3234"));
        exchange3.setEur(new BigDecimal("1.3234"));
        exchange3.setGbp(new BigDecimal("1.3234"));
        exchange3.setJpy(new BigDecimal("1.3234"));
        exchange3 = exchangeDao.addExchange(exchange3);

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

        List<Integer> quantities = new ArrayList<>();
        quantities.add(5);
        quantities.add(10);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.parse("2021-03-08"));
        purchase.setCurrency("CAD");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);
        purchase.setQuantities(quantities);
        purchase = purchaseDao.addPurchase(purchase);

        Purchase purchase2 = new Purchase();
        purchase2.setPurchaseDate(LocalDate.parse("2021-03-21"));
        purchase2.setCurrency("EUR");
        purchase2.setExchange(exchange2);
        purchase2.setCustomer(customer2);
        purchase2.setItems(items);
        purchase2.setQuantities(quantities);
        purchase2 = purchaseDao.addPurchase(purchase2);

        Purchase purchase3 = new Purchase();
        purchase3.setPurchaseDate(LocalDate.parse("2021-04-01"));
        purchase3.setCurrency("EUR");
        purchase3.setExchange(exchange3);
        purchase3.setCustomer(customer2);
        purchase3.setItems(items);
        purchase3.setQuantities(quantities);
        purchase3 = purchaseDao.addPurchase(purchase3);

        List<Purchase> fromDao = purchaseDao.getPurchasesByCurrency("EUR");
        assertEquals(2, fromDao.size());
        assertFalse(fromDao.contains(purchase));
        assertTrue(fromDao.contains(purchase2));
        assertTrue(fromDao.contains(purchase3));
    }
}