package tsg.team5.ecommerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tsg.team5.ecommerce.dao.*;
import tsg.team5.ecommerce.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import tsg.team5.ecommerce.service.MoneyManip;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    ExchangeDao exchangeDao;

    @Autowired
    ItemDao itemDao;

    @ResponseBody
    @PostMapping("makePurchase")
    public String makePurchase(@RequestBody String purchase) throws JSONException, JsonProcessingException {

        JSONObject info = JSONObject.fromObject(purchase);
        //System.out.println(info);

        String purchaseCurrency = info.getString("currency");
        LocalDate date = LocalDate.now();
        int customerId = info.getInt("customerId");

        Exchange exchange1 = new Exchange();
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
        List<Integer> quantityList = new ArrayList<>();
        JSONObject cartItem;
        Item item;

        for(int i = 0; i < items.size(); i++){
            cartItem = items.getJSONObject(i);

            item = new Item();
            item.setItemId(cartItem.getInt("itemId"));
            item.setPrice(cartItem.getDouble("price"));
            item.setCategory(cartItem.getString("category"));
            item.setItemName(cartItem.getString("title"));
            quantityList.add(cartItem.getInt("quantity"));

            itemList.add(item);
            itemDao.addItem(item);
        }

        //use this section to populate the purchase object with data
        //get the exchangeID from the above exchange
        Purchase purchase1 = new Purchase();
        purchase1.setCurrency(purchaseCurrency);
        purchase1.setPurchaseDate(date);
        purchase1.setCustomer(customer);
        purchase1.setExchange(exchange);
        purchase1.setItems(itemList);
        purchase1.setQuantities(quantityList);
        purchaseDao.addPurchase(purchase1);

        //System.out.println("did it work?");
        double total = MoneyManip.calculateTotalInvoice(purchase1);
        //System.out.println(total);

        ReactService passInfo = new ReactService();
        passInfo.setCurrency(purchaseCurrency);
        passInfo.setTotalCost(total);

        ObjectMapper mapper = new ObjectMapper();
        //System.out.println(mapper.writeValueAsString(passInfo));
        return mapper.writeValueAsString(passInfo);
    }

    @ResponseBody
    @PostMapping("findTotal")
    public String cardData(@RequestBody String cart) throws JSONException, JsonProcessingException {

        JSONObject info = JSONObject.fromObject(cart);
        System.out.println(info);
        System.out.println("second function above this");
        JSONArray items = info.getJSONArray("cart");
        String currency = info.getString("currency");
        double rate;
        if(currency.equals("CAD")) {
            rate = info.getJSONObject("exchange").getDouble("CAD");
        }else if(currency.equals("EUR")) {
            rate = info.getJSONObject("exchange").getDouble("EUR");
        }else if(currency.equals("GBP")) {
            rate = info.getJSONObject("exchange").getDouble("GBP");
        }else if(currency.equals("JPY")) {
            rate = info.getJSONObject("exchange").getDouble("JPY");
        }else if(currency.equals("CNY")) {
            rate = info.getJSONObject("exchange").getDouble("CNY");
        }else{
            rate = 1;
        }

        JSONObject test2;
        JSONArray test3 = new JSONArray();
        JSONObject finalData = new JSONObject();


        for(int i = 0; i < items.size(); i++){
            test2 = items.getJSONObject(i);
            int itemId = test2.getInt("itemId");
            String title = test2.getString("title");
            Double price = test2.getDouble("price");
            int quantity = test2.getInt("quantity");
            String category = test2.getString("category");


            double adjPrice = MoneyManip.evaluatePriceForRate(price, BigDecimal.valueOf(rate));
            double totalCard = MoneyManip.evaluateTotalPriceForQuantity(adjPrice, quantity);
            BigDecimal totalCardB = new BigDecimal(Double.toString(totalCard));
            totalCardB = totalCardB.setScale(2, RoundingMode.HALF_UP);

            String correctTotalCard = String.valueOf(totalCardB.doubleValue());


            finalData.accumulate("itemId", itemId);
            finalData.accumulate("title", title);
            finalData.accumulate("price", price);
            finalData.accumulate("quantity", quantity);
            finalData.accumulate("category", category);
            finalData.accumulate("totalForCard", correctTotalCard);

            test3.add(finalData);
            finalData.clear();

        }

        //ObjectMapper mapper = new ObjectMapper();
        //System.out.println(mapper.writeValueAsString(test));

        System.out.println(test3.toString());
        return test3.toString();
    }
}
