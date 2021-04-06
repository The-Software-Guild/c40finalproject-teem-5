package tsg.team5.ecommerce.controller;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tsg.team5.ecommerce.dao.*;
import tsg.team5.ecommerce.entity.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("cart/")

public class PurchaseController {

    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    ExchangeDao exchangeDao;

    @Autowired
    ItemDao itemDao;

    @ResponseBody
    @PostMapping("makePurchase")
    public Purchase makePurchase(@RequestBody String purchase) throws JSONException {

        //Use this section to parse all relevant data from the JSON received
        JSONObject info = new JSONObject(purchase);
        String purchaseCurrency = info.getString("currency");
        LocalDate date = LocalDate.now();
        //int customerId = info.getInt("customerId");
        //int addressId = info.getInt("addressId");

        Address address1 = new Address();
        address1.setStreet("real St.");
        address1.setCity("real-city");
        address1.setState("NJ");
        address1.setPostal("11223344");
        address1.setCountry("country");
        Address address2 = addressDao.addAddress(address1);

        Item item = new Item();
        item.setItemId(1);
        item.setItemName("example1");
        item.setCategory("category1");
        item.setPrice(2.10);
        item.setQuantity(5);
        item = itemDao.addItem(item);

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);


        Customer customer1 = new Customer();
        customer1.setCustomerName("Mr.Fake");
        customer1.setAddress(address2);
        customer1.setCustomerEmail("fake@email.com");
        customer1.setCustomerPhone("5555555555");
        Customer customer2 = customerDao.addCustomer(customer1);



        //Address address = addressDao.getAddressById(addressId);
        //address may be adjusted based on front page

        //add in a .setUsd method back into the exchange entity
        Exchange exchange1 = new Exchange();
        exchange1.setCny(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("CNY")));
        exchange1.setCad(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("CAD")));
        exchange1.setGbp(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("GBP")));
        exchange1.setEur(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("EUR")));
        exchange1.setCny(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("CNY")));
        exchange1.setJpy(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("JPY")));

        exchange1 = exchangeDao.addExchange(exchange1);


        //use this section to populate the purchase object with data
        //get the exchangeID from the above exchange
        Purchase purchase1 = new Purchase();
        purchase1.setCurrency(purchaseCurrency);
        purchase1.setPurchaseDate(date);
        purchase1.setCustomer(customer2);
        purchase1.setExchange(exchange1);
        purchase1.setItems(itemList);

        purchaseDao.addPurchase(purchase1);
        System.out.println("did it work?");




        //Print json passed in for debugging
        System.out.println(purchase);


        return null;
    }

}
