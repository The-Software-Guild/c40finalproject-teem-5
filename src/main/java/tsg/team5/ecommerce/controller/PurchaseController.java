package tsg.team5.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tsg.team5.ecommerce.dao.PurchaseDao;
import tsg.team5.ecommerce.entity.Purchase;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("cart/")
public class PurchaseController {

    @Autowired
    PurchaseDao purchaseDao;

    @PostMapping("makePurchase")
    public Purchase makePurchase(@RequestBody Purchase purchase){

        System.out.println(purchase.getPurchaseId());
        System.out.println("axios contact");

        return null;
    }

}
