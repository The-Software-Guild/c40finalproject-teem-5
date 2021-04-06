package tsg.team5.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tsg.team5.ecommerce.dao.PurchaseDao;
import tsg.team5.ecommerce.entity.Purchase;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("cart/")

public class PurchaseController {

    @Autowired
    PurchaseDao purchaseDao;

    @ResponseBody
    @PostMapping("makePurchase")
    public Purchase makePurchase(@RequestBody String purchase){


        System.out.println(purchase);
        System.out.println("axios contact");

        return null;
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
