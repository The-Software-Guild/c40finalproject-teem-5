package tsg.team5.ecommerce.service;

import org.junit.jupiter.api.Test;
import tsg.team5.ecommerce.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoneyManipTest {

    @Test
    void evaluatePriceForRate() {
        double price = 2.25;
        BigDecimal rate = new BigDecimal(0.8529);
        double real = MoneyManip.evaluatePriceForRate(price, rate);
        assertEquals(1.919025, real);
    }

    @Test
    void evaluateTotalPriceForQuantity() {
        double price = 2.25;
        int qty = 4;
        double total = MoneyManip.evaluateTotalPriceForQuantity(price, qty);
        assertEquals(9, total);
    }

    @Test
    void calculateTotalInvoiceCAD() {
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item.setQuantity(5);

        Item item2 = new Item();
        item2.setItemId(2);
        item2.setItemName("example2");
        item2.setCategory("category2");
        item2.setPrice(2.50);
        item2.setQuantity(10);

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.0000"));
        exchange.setCad(new BigDecimal("2.0000"));
        exchange.setEur(new BigDecimal("3.0000"));
        exchange.setGbp(new BigDecimal("4.0000"));
        exchange.setJpy(new BigDecimal("5.0000"));

        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setCountry("Country 1");
        address.setState("TX");
        address.setPostal("85490");

        Customer customer = new Customer();
        customer.setCustomerName("Name 1");
        customer.setCustomerEmail("abc@abc.com");
        customer.setCustomerPhone("3879894356");
        customer.setAddress(address);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setCurrency("CAD");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);

        double value = MoneyManip.calculateTotalInvoice(purchase);
        assertEquals(71, value);
    }

    @Test
    void calculateTotalInvoiceEUR() {
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item.setQuantity(5);

        Item item2 = new Item();
        item2.setItemId(2);
        item2.setItemName("example2");
        item2.setCategory("category2");
        item2.setPrice(2.50);
        item2.setQuantity(10);

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.0000"));
        exchange.setCad(new BigDecimal("2.0000"));
        exchange.setEur(new BigDecimal("3.0000"));
        exchange.setGbp(new BigDecimal("4.0000"));
        exchange.setJpy(new BigDecimal("5.0000"));

        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setCountry("Country 1");
        address.setState("TX");
        address.setPostal("85490");

        Customer customer = new Customer();
        customer.setCustomerName("Name 1");
        customer.setCustomerEmail("abc@abc.com");
        customer.setCustomerPhone("3879894356");
        customer.setAddress(address);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setCurrency("EUR");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);

        double value = MoneyManip.calculateTotalInvoice(purchase);
        assertEquals(106.5, value);
    }

    @Test
    void calculateTotalInvoiceGBP() {
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item.setQuantity(5);

        Item item2 = new Item();
        item2.setItemId(2);
        item2.setItemName("example2");
        item2.setCategory("category2");
        item2.setPrice(2.50);
        item2.setQuantity(10);

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.0000"));
        exchange.setCad(new BigDecimal("2.0000"));
        exchange.setEur(new BigDecimal("3.0000"));
        exchange.setGbp(new BigDecimal("4.0000"));
        exchange.setJpy(new BigDecimal("5.0000"));

        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setCountry("Country 1");
        address.setState("TX");
        address.setPostal("85490");

        Customer customer = new Customer();
        customer.setCustomerName("Name 1");
        customer.setCustomerEmail("abc@abc.com");
        customer.setCustomerPhone("3879894356");
        customer.setAddress(address);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setCurrency("GBP");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);

        double value = MoneyManip.calculateTotalInvoice(purchase);
        assertEquals(142, value);
    }

    @Test
    void calculateTotalInvoiceJPY() {
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item.setQuantity(5);

        Item item2 = new Item();
        item2.setItemId(2);
        item2.setItemName("example2");
        item2.setCategory("category2");
        item2.setPrice(2.50);
        item2.setQuantity(10);

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.0000"));
        exchange.setCad(new BigDecimal("2.0000"));
        exchange.setEur(new BigDecimal("3.0000"));
        exchange.setGbp(new BigDecimal("4.0000"));
        exchange.setJpy(new BigDecimal("5.0000"));

        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setCountry("Country 1");
        address.setState("TX");
        address.setPostal("85490");

        Customer customer = new Customer();
        customer.setCustomerName("Name 1");
        customer.setCustomerEmail("abc@abc.com");
        customer.setCustomerPhone("3879894356");
        customer.setAddress(address);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setCurrency("JPY");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);

        double value = MoneyManip.calculateTotalInvoice(purchase);
        assertEquals(177.5, value);
    }

    @Test
    void calculateTotalInvoiceCNY() {
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item.setQuantity(5);

        Item item2 = new Item();
        item2.setItemId(2);
        item2.setItemName("example2");
        item2.setCategory("category2");
        item2.setPrice(2.50);
        item2.setQuantity(10);

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("0.5000"));
        exchange.setCad(new BigDecimal("2.0000"));
        exchange.setEur(new BigDecimal("3.0000"));
        exchange.setGbp(new BigDecimal("4.0000"));
        exchange.setJpy(new BigDecimal("5.0000"));

        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setCountry("Country 1");
        address.setState("TX");
        address.setPostal("85490");

        Customer customer = new Customer();
        customer.setCustomerName("Name 1");
        customer.setCustomerEmail("abc@abc.com");
        customer.setCustomerPhone("3879894356");
        customer.setAddress(address);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setCurrency("CNY");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);

        double value = MoneyManip.calculateTotalInvoice(purchase);
        assertEquals(17.75, value);
    }

    @Test
    void calculateTotalInvoiceUSD() {
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item.setQuantity(5);

        Item item2 = new Item();
        item2.setItemId(2);
        item2.setItemName("example2");
        item2.setCategory("category2");
        item2.setPrice(2.50);
        item2.setQuantity(10);

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        Exchange exchange = new Exchange();
        exchange.setCny(new BigDecimal("1.0000"));
        exchange.setCad(new BigDecimal("2.0000"));
        exchange.setEur(new BigDecimal("3.0000"));
        exchange.setGbp(new BigDecimal("4.0000"));
        exchange.setJpy(new BigDecimal("5.0000"));

        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setCountry("Country 1");
        address.setState("TX");
        address.setPostal("85490");

        Customer customer = new Customer();
        customer.setCustomerName("Name 1");
        customer.setCustomerEmail("abc@abc.com");
        customer.setCustomerPhone("3879894356");
        customer.setAddress(address);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setCurrency("USD");
        purchase.setExchange(exchange);
        purchase.setCustomer(customer);
        purchase.setItems(items);

        double value = MoneyManip.calculateTotalInvoice(purchase);
        assertEquals(35.5, value);
    }
}