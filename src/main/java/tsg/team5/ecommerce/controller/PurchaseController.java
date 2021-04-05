package tsg.team5.ecommerce.controller;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tsg.team5.ecommerce.dao.PurchaseDao;
import tsg.team5.ecommerce.entity.Purchase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("cart/")

public class PurchaseController {

    @Autowired
    PurchaseDao purchaseDao;

    @ResponseBody
    @PostMapping("makePurchase")
    public Purchase makePurchase(@RequestBody String purchase) throws JSONException {

        //Use this section to parse all relevant data from the JSON received
        JSONObject info = new JSONObject(purchase);
        int purchaseId = info.getInt("purchaseID");
        String purchaseCurrency = info.getString("currency");
        System.out.println(purchaseCurrency);

        LocalDate date = LocalDate.now();

        //use this section to populate the purchase object with data
        Purchase purchase1 = new Purchase();
        purchase1.setPurchaseId(purchaseId);
        purchase1.setCurrency(purchaseCurrency);
        purchase1.setPurchaseDate(date);

        //Print json passed in for debugging
        System.out.println(purchase);


        return null;
    }

}
