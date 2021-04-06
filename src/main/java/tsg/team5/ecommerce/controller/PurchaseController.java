package tsg.team5.ecommerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tsg.team5.ecommerce.dao.PurchaseDao;
import tsg.team5.ecommerce.entity.Purchase;

import tsg.team5.ecommerce.dao.*;
import tsg.team5.ecommerce.entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import tsg.team5.ecommerce.service.MoneyManip;


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
    public String makePurchase(@RequestBody String purchase) throws JSONException, JsonProcessingException {
        //Use this section to parse all relevant data from the JSON received
        JSONObject info = new JSONObject(purchase);
        String purchaseCurrency = info.getString("currency");
        LocalDate date = LocalDate.now();
        int customerId = info.getInt("customerId");


        Exchange exchange1 = new Exchange();
        exchange1.setCny(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("CNY")));
        exchange1.setCad(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("CAD")));
        exchange1.setGbp(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("GBP")));
        exchange1.setEur(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("EUR")));
        exchange1.setCny(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("CNY")));
        exchange1.setJpy(BigDecimal.valueOf(info.getJSONObject("exchange").getDouble("JPY")));

        exchange1 = exchangeDao.addExchange(exchange1);

        Customer customer = customerDao.getCustomerById(customerId);
        Exchange exchange = exchangeDao.getExchangeById(exchange1.getExchangeId());

        JSONArray items = info.getJSONArray("cartData");
        List<Item> itemList = new ArrayList<>();
        JSONObject cartItem;
        Item item;

        for(int i = 0; i < items.length(); i++){
            cartItem = items.getJSONObject(i);
            item = new Item();
            item.setItemId(cartItem.getInt("itemId"));
            item.setPrice(cartItem.getDouble("price"));
            item.setQuantity(cartItem.getInt("quantity"));
            item.setCategory(cartItem.getString("category"));
            item.setItemName(cartItem.getString("title"));
            itemList.add(item);
        }
        //use this section to populate the purchase object with data
        //get the exchangeID from the above exchange
        Purchase purchase1 = new Purchase();
        purchase1.setCurrency(purchaseCurrency);
        purchase1.setPurchaseDate(date);
        purchase1.setCustomer(customer);
        purchase1.setExchange(exchange);
        purchase1.setItems(itemList);

        purchaseDao.addPurchase(purchase1);
        System.out.println("did it work?");

        double total = MoneyManip.calculateTotalInvoice(purchase1);
        System.out.println(total);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(purchase1));
        return mapper.writeValueAsString(purchase1);
    }




   /* @ResponseBody
    @GetMapping
    public List<Purchase> getCustomerData(int id){
        return purchaseDao.getPurchasesForCustomer(id);
    }


    @ResponseBody
    @GetMapping
    public List<Purchase> getCurrencyData(String currency){
        return purchaseDao.getPurchasesByCurrency(currency);
    }

    @ResponseBody
    @GetMapping
    public List<Purchase> getSpecificDateData(LocalDate date){
        return purchaseDao.getPurchasesByDate(date);

    }

    @ResponseBody
    @GetMapping
    public List<Purchase> getRangedDateData(LocalDate from, LocalDate to){
        return purchaseDao.getPurchasesRangeDate(from, to);
    }*/
}
